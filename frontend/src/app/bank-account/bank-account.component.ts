import {CommonModule, DatePipe, DecimalPipe, NgClass, NgIf} from "@angular/common";
import { Component, OnInit } from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {async, catchError, map, Observable, switchMap, throwError} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../model/customer.model";
import {Account} from "../model/customer-accounts";
import {AuthService} from "../services/auth.service";
@Component({
  selector: 'app-bank-account',
  standalone: true,
  imports: [
    NgIf,
    DatePipe,
    DecimalPipe,
    NgClass,
    CommonModule,
  ],
  templateUrl: './bank-account.component.html',
  styleUrl: './bank-account.component.css'
})
export class BankAccountComponent implements OnInit {
  bankAccounts$!: Observable<Account[]>;
  customer$!: Observable<Customer>;
  customerId!:string;
  customer!: Customer;
  errorMessage!: Object;
  constructor(
    private customerService: CustomerService,
    private router: Router,
    private route: ActivatedRoute,private auth:AuthService) {
    this.customer = this.router.getCurrentNavigation()?.extras.state as Customer;


  }

  onClickButton(accountId: string) {
    console.log(accountId);
    this.router.navigate(['admin/accounts', accountId]);
  }

  ngOnInit(): void {


    this.customerId = this.route.snapshot.params['id'];
    if(this.auth.roles.includes('USER')) {
      this.bankAccounts$ = this.customerService.getAllBankAccounts().pipe(
        map((bankAccounts) => {
          // Use the map operator to transform the data emitted by bankAccounts$
          return bankAccounts.filter((account) => {
            return account.customerDTO.name === this.auth.username; // Use '===' for strict equality check
          });
        }),
        catchError((err) => {
          // Handle errors
          this.errorMessage = err.message;
          return throwError(err);
        })
      );

      console.log("test"+JSON.stringify(this.bankAccounts$))
    }else{
      this.bankAccounts$ = this.customerService.getAllBankAccounts().pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(err);
        }));
    }

  }

  handleCustomerPageFromBankAccounts(customer: Customer) {
    this.router.navigateByUrl("/customers/" + customer.id, {state: customer}).then(r => {
    })
  }



}

