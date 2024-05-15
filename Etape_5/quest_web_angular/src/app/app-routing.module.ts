import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/user/login/login.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { authGuard } from './core/auth.guard';
import { RegisterComponent } from './pages/user/register/register.component';
import { AddressComponent } from './pages/address/address.component';
import { AddressFormComponent } from './pages/address/address-form/address-form.component';
import { ProfileComponent } from './pages/user/profile/profile.component';
import { UserComponent } from './pages/user/user.component';
import { NavbarComponent } from './pages/components/navbar/navbar.component';
import { UserFormComponent } from './pages/user/user-form/user-form.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: '',
    component: NavbarComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [authGuard],
      },
      {
        path: 'user',
        component: UserComponent,
        canActivate: [authGuard],
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [authGuard],
      },
      {
        path: 'address',
        component: AddressComponent, // Add AddressComponent for '/address' route
        canActivate: [authGuard],
      },
      {
        path: 'address/user/:id',
        component: AddressComponent, // Add AddressComponent for '/address' route
        canActivate: [authGuard],
      },
      {
        path: 'address-form',
        component: AddressFormComponent, // Add AddressComponent for '/address' route
        canActivate: [authGuard],
      },
      {
        path: 'address-form/:id',
        component: AddressFormComponent, // Add AddressComponent for '/address' route
        canActivate: [authGuard],
      },
      {
        path: 'user',
        component: UserComponent,
        canActivate: [authGuard],
      },
      {
        path: 'user-form',
        component: UserFormComponent, // Make sure to import UserFormComponent at the top
        canActivate: [authGuard],
      },
      {
        path: 'user-form/:id',
        component: UserFormComponent, // Make sure to import UserFormComponent at the top
        canActivate: [authGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
