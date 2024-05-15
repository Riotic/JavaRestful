import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { AddressService } from '../../../core/services/address.service';
import { IAddress } from '../../../core/models/common.model';
import e from 'express';

@Component({
  selector: 'app-address-form',
  templateUrl: './address-form.component.html',
  styleUrl: './address-form.component.css'
})
export class AddressFormComponent implements OnInit {
  addresseForm!: FormGroup;
  id = '';
  ngOnInit(): void {
      this.activatedRoute.params.subscribe((params) => {
        if(params['id']) {
          this.id = params['id'];
          this.getAdressById(this.id);
        }
      });
  }

  constructor(
    private fb: FormBuilder, 
    private addressService: AddressService, 
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.addresseForm = this.fb.group({
      street: new FormControl('', [Validators.required ]),
      postalCode: new FormControl('', [Validators.required ]),
      city: new FormControl('', [Validators.required ]),
      country: new FormControl('', [Validators.required ]),
    });
  }

  onSubmit() {
    if(this.addresseForm.valid) {
      if(this.id !== '' ) {
        this.addressService.updateAddress(this.id, this.addresseForm.value).subscribe((res: any) => {
          this.router.navigate(['/address']);
        });
      } else {   
        this.addressService.addAddress(this.addresseForm.value).subscribe((res: any) => {
          this.router.navigate(['/address']);
        });
      } 
    } else {
      this.addresseForm.markAllAsTouched();
    }
  }

  getAdressById(id: string) {
    this.addressService.getOneAddress(id).subscribe((res: any) => {
      this.addresseForm.patchValue(res);
    } );
  }
}
