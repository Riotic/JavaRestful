import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { IAddress } from '../models/common.model';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private dbPath = 'http://localhost:8090/address';

  constructor(private http: HttpClient) { }

  // Create a new address
  addAddress(address: IAddress) {
    return this.http.post('http://localhost:8090/address', address);
  }

  // Get all addresses
  getAllAddresses() {
    return this.http.get(this.dbPath).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Get all addresses by user id
  getAllAddressesByUserId(userId: string) {
    return this.http.get(`${this.dbPath}/user/${userId}`).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Get one address by id
  getOneAddress(id: string) {
    return this.http.get(`${this.dbPath}/${id}`).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Update an existing address
  updateAddress(id: string, data: any) {
    return this.http.put(`${this.dbPath}/${id}`, data).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }

  // Delete an address
  deleteAddress(id: string) {
    return this.http.delete(`${this.dbPath}/${id}`).pipe(
      catchError(error => {
        console.error('Error occurred:', error);
        return throwError(error);
      })
    );
  }
}