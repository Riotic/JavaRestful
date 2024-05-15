import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  userDetails: any;

  constructor(private http: HttpClient) { }

  // Supposons que vous ayez une méthode pour obtenir le token JWT
  getToken(): string {
    // Récupérez le token JWT de là où il est stocké (par exemple, localStorage)
    return localStorage.getItem('token') || '';
  }

  fetchUserDetails(): Observable<any> {
    return this.http.get('http://localhost:8090/me').pipe(
      tap(data => {
        this.userDetails = data;
      })
    );
  }

  getUserDetails() {
    if (!this.userDetails) {
      this.fetchUserDetails().subscribe();
    }
    return this.userDetails;
  }
}