import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {  ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  loginObj: Login;

  constructor(private http: HttpClient,private router: Router, private route: ActivatedRoute) {
    this.loginObj = new Login();
    if(typeof localStorage !== 'undefined') {
        localStorage.setItem('angular17token', '');
    } else if (typeof sessionStorage !== 'undefined') {
      // Fallback to sessionStorage if localStorage is not supported
      sessionStorage.setItem('angular17token', '');
    } else {
      // If neither localStorage nor sessionStorage is supported
      console.log('Web Storage is not supported in this environment.');
    }
  }

  ngOnInit() {
    localStorage.removeItem('angular17token');
    localStorage.setItem('angular17token', '');
  }

  onLogin() {

    this.http.post('http://localhost:8090/authenticate', this.loginObj).subscribe((res:any)=>{
      if(res.token) {
        alert("Login Success");
                // Supprimer le token existant
        localStorage.removeItem('angular17token');
        localStorage.setItem('angular17token', res.token)
        this.router.navigateByUrl('/dashboard')
      } else {
        alert("Login failed")
      }
    }, error => {
      alert("Login failed: " + error.message);
    })
  }
}

export class Login { 
    username: string;
    password: string;
    constructor() {
      this.username = '';
      this.password = '';
    } 
}