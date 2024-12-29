import {Component, NgModule} from '@angular/core';
import { NavbarComponent } from "../navbar/navbar.component";
import {RouterModule, RouterOutlet} from '@angular/router';
import {SidebarComponent} from "../sidebar/sidebar.component";
import {CommonModule} from "@angular/common";
import {DashboardComponent} from "../dashboard/dashboard.component";
import {CustomersComponent} from "../customers/customers.component";
import {AccountComponent} from "../account/account.component";
import {AuthService} from "../services/auth.service";

@Component({
    selector: 'app-admin-template',
    standalone: true,
    templateUrl: './admin-template.component.html',
    styleUrl: './admin-template.component.css',
  imports: [NavbarComponent, RouterOutlet, SidebarComponent, CommonModule, DashboardComponent, CustomersComponent, AccountComponent]
})


export class AdminTemplateComponent {
  constructor(public auth:AuthService) {
  }

}
