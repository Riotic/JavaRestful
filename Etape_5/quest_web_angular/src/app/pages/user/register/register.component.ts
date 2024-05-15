import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { request } from 'http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerObj: Register;

  constructor(private http: HttpClient,private router: Router) {
    this.registerObj = new Register();
  }

  onRegister() {
    this.http.post('http://localhost:8090/register', this.registerObj).subscribe((res:any)=>{
      if(res.code === 201) {
        alert("Register Success");
        this.router.navigateByUrl('/login')
      } else {
        alert("Register failed")
      }
    }, error => {
      alert("Register failed: " + error.message + " " + error.error.errors);
    })
  }
}

export class Register { 
    username: string;
    password: string;
    constructor() {
      this.username = '';
      this.password = '';
    } 
}