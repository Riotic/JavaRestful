import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { AuthService } from '../../core/auth.service';
import { IUser } from '../../core/models/common.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users: IUser[] = [];
  userId: string = '';

  constructor(
    private userService: UserService, 
    private router: Router,
    private route: ActivatedRoute,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.userId = params.get('userId')!;
      if (this.userId) {
        this.getUserById();
      } else {
        this.getAllUsers();
      }
    });
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((res: any) => {
      this.users = res;
      console.log(this.users);
    });
  }

  getUserById() {
    this.userService.getOneUser(this.userId).subscribe((res: any) => {
      this.users = [res];
      console.log(this.users);
    });
  }

  editUser(id: string) {
    this.router.navigate(['/user-form', id]);
  }

  deleteUser(id: string) {
    if(window.confirm('Etes vous sur de vouloir supprimer cet utilisateur?')) {   
      this.userService.deleteUser(id).subscribe((res: any) => {
        // If the deleted user is the currently logged in user, redirect to login
        if (this.authService.getUserDetails().id === id) {
          this.router.navigate(['/login']);
        } else {
          this.getAllUsers();
        }
      });
    }
  }
}