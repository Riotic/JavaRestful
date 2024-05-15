import { Component, OnInit } from '@angular/core';
import { AddressService } from '../../core/services/address.service';
import { AuthService } from '../../core/auth.service'; // Assurez-vous que le chemin d'importation est correct
import { IAddress } from '../../core/models/common.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  addresses: IAddress[] = [];
  userId: string = '';

  constructor(
    private addressService: AddressService, 
    private router: Router,
    private route: ActivatedRoute,
    public authService: AuthService // Injectez AuthService ici
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.userId = params.get('id')!; 
      if (this.userId) {
        this.getAllAddressesByUserId();
      } else {
        this.getAllAddresses();
      }
    });
  }

  getAllAddresses() {
    this.addressService.getAllAddresses().subscribe((res: any) => {
      this.addresses = res;
      console.log(this.addresses);
    });
  }

  getAllAddressesByUserId() {
    this.addressService.getAllAddressesByUserId(this.userId).subscribe((res: any) => {
      this.addresses = res;
      console.log(this.addresses);
    });
  }

  editAddress(id: string) {
    this.router.navigate(['/address-form', id]);
  }

  deleteAddress(id: string) {
    if(window.confirm('Etes vous sur de vouloir supprimer cette addresse?')) {   
      this.addressService.deleteAddress(id).subscribe((res: any) => {
        this.getAllAddresses();
      });
    }
  }
}