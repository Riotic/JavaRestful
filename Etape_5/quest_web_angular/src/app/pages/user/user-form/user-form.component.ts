import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { UserService } from '../../../core/services/user.service';
import { IUser } from '../../../core/models/common.model';
import { AuthService } from '../../../core/auth.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  userForm!: FormGroup;
  userId = '';

  constructor(
    private fb: FormBuilder, 
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public authService: AuthService 
  ) {
    this.userForm = this.fb.group({
      username: new FormControl(''),
      role: new FormControl(''),
      password: new FormControl(''), // Add password field
    });
  }
  
  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.paramMap.get('id')!;
  
    if (this.userId) {
      this.userService.getOneUser(this.userId).subscribe((user: any) => {
        this.userForm.patchValue({
          username: user.username,
          role: user.role,
        });
      }, error => {
        console.error('Error occurred:', error);
      });
    }
  
    console.log(this.authService.getUserDetails());
  }

  onSubmit() {
    if(this.userForm.valid) {
      let userData = this.userForm.value;
      if (userData.password === '') {
        delete userData.password; // Remove password field if it's empty
      }
      if(this.userId !== '' ) {
        this.userService.updateUser(this.userId, userData).subscribe((res: any) => {
          if (this.authService.getUserDetails().role === 'ROLE_ADMIN' && this.authService.getUserDetails().id !== this.userId) {
            this.router.navigate(['/user']);
          } else {
            this.router.navigate(['/login']);
          }
        });
      } else {   
        if (!userData.password) {
          alert('Mot de passe requis pour créer un nouvel utilisateur.');
          return;
        }
        this.userService.addUser(userData).subscribe((res: any) => {
          this.router.navigate(['/user']);
        });
      } 
    } else {
      this.userForm.markAllAsTouched();
    }
  }

  getUserById(id: string) {
    this.userService.getOneUser(id).subscribe((res: any) => {
      this.userForm.patchValue(res);
    });
  }
}