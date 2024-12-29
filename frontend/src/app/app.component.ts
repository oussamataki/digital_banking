import { Component, NgModule, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterModule, RouterOutlet } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import { HttpClientModule } from '@angular/common/http';
import { CustomerService } from './services/customer.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/auth.service';
import {SidebarComponent} from "./sidebar/sidebar.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {NgApexchartsModule} from "ng-apexcharts";
import {HighchartsChartModule} from "highcharts-angular";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    RouterModule,
    RouterLink,
    HttpClientModule,
    RouterLinkActive,
    RouterOutlet,
    NavbarComponent,
    ReactiveFormsModule,FormsModule,
    SidebarComponent,
    DashboardComponent,
    NgApexchartsModule,
    HighchartsChartModule,



  ]
})



export class AppComponent implements OnInit{
  constructor(private authService:AuthService){}
  ngOnInit() {
    this.authService.loadJwtTokenFromLocalStorage()
  }

  title = 'frentend-banking';
}
