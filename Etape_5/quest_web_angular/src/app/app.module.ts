import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';
import { FormsModule, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {
  provideHttpClient,
  withInterceptors,
  HttpClientModule,
  HttpClient,
  withFetch,
} from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { LoginComponent } from './pages/user/login/login.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { RegisterComponent } from './pages/user/register/register.component';
import { authGuard } from './core/auth.guard';
import { customInterceptor } from './core/custom.interceptor';
import { NavbarComponent } from './pages/components/navbar/navbar.component';
import { AddressComponent } from './pages/address/address.component';
import { UserComponent } from './pages/user/user.component';
import { ProfileComponent } from './pages/user/profile/profile.component';
import { AddressFormComponent } from './pages/address/address-form/address-form.component';
import { UserFormComponent } from './pages/user/user-form/user-form.component';
import { AuthService } from './core/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayoutComponent,
    DashboardComponent,
    RegisterComponent,
    NavbarComponent,
    AddressComponent,
    UserComponent,
    ProfileComponent,
    AddressFormComponent,
    UserFormComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, RouterOutlet, ReactiveFormsModule, HttpClientModule],
  providers: [
    provideClientHydration(),
    provideHttpClient(withInterceptors([customInterceptor])),
    provideHttpClient(withFetch()),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
