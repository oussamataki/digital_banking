import {Component, NgModule, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {RouterOutlet} from "@angular/router";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {AuthService} from "../services/auth.service";


declare const $: any;
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/admin/accounts', title: 'Dashboard',  icon: 'dashboard', class: '' },
  { path: '/user-profile', title: 'User Profile',  icon:'person', class: '' },
  { path: '/clients', title: 'Clients',  icon:'content_paste', class: '' },
  { path: '/accounts', title: 'Accounts',  icon:'library_books', class: '' },
  { path: '/transaction', title: 'Transaction History Page',  icon:'bubble_chart', class: '' },
  { path: '/management', title: 'Account Management',  icon:'account_circle', class: '' },
  { path: '/notifications', title: 'Notifications',  icon:'notifications', class: '' },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  standalone:true,
  imports: [SidebarComponent,RouterOutlet,CommonModule,RouterModule]
})

export class SidebarComponent implements OnInit {
  menuItems!: any[];


  constructor(public auth:AuthService) {

  }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  };

  isSecondSidebarVisible: boolean = false;

  toggleSecondSidebar() {
    this.isSecondSidebarVisible = !this.isSecondSidebarVisible;
  }


}
