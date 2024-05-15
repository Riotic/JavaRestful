import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private dbPath = 'http://localhost:8090/user';

  constructor(private http: HttpClient) { }

  // Get all users
  getAllUsers() {
    return this.http.get(this.dbPath).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Get the current user
  getCurrentUser() {
    return this.http.get('http://localhost:8090/me').pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Get one user by id
  getOneUser(id: string) {
    return this.http.get(`${this.dbPath}/${id}`).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Add a new user
  addUser(data: any) {
    return this.http.post(this.dbPath, data).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Update an existing user
  updateUser(id: string, data: any) {
    return this.http.put(`${this.dbPath}/${id}`, data).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Delete a user
  deleteUser(id: string) {
    return this.http.delete(`${this.dbPath}/${id}`).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }
}