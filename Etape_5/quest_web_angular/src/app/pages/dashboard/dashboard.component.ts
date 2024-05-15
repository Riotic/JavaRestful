import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {


  users:any[]=[];
  constructor(private http: HttpClient) {

  }
  ngOnInit(): void {
    this.getAllusers();
  }

  getAllusers() {
    debugger;
    this.http.get('http://localhost:8090/user').subscribe((res:any) => {
      this.users = res.data;  
    } , error => {
      alert("Error From API")
    })
  }

}