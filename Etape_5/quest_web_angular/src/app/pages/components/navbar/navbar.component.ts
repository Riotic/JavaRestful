import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  onLogout() {
    // Supprimer le token
    localStorage.removeItem('angular17token');

    // Vider le cache et rafraîchir la page
    location.reload();
  }

}