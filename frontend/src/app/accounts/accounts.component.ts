import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AccountService } from '../services/account.service';
import { Observable, catchError, throwError } from 'rxjs';
import { AccountDetails } from '../model/account.model';
import { AuthService } from '../services/auth.service';
import Swal from "sweetalert2";
import {ActivatedRoute} from "@angular/router";
import {AuditLog} from "../model/auditLog.model";

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit {

  accountFormGroup!: FormGroup;
  currentPage: number = 0;
  pageSize: number = 5;
  accountObserveble!: Observable<AccountDetails>
  operationFormGroup!: FormGroup;
  errorAccountMessage!: Object;
  errorMessage!:Object;
  private account: any;
  private customerId: any;


  constructor(private route: ActivatedRoute,private fb: FormBuilder, private accountService: AccountService,public authService:AuthService) {
    this.handleSearchAccoun();
  }
  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId: this.fb.control("")
    });
    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control(null),
      amount: this.fb.control(0),
      description: this.fb.control(null),
      accountDestination: this.fb.control(null)
    });


  }
  handleSearchAccoun() {
    this.route.params.subscribe(params => {
       this.account = params['id'];

      this.accountObserveble = this.accountService.getAccount(this.account, this.currentPage, this.pageSize).pipe(
        catchError(err => {
          Swal.fire({
            icon: 'error',
            title: 'Account not found',
            text: 'The specified account could not be found. Please check the account ID and try again.',
          });
          this.errorAccountMessage = 'Account not found';
          return throwError(err);
        })
      );
    });
  }
  handleSearchAccount() {
    let accountId =this.account;
    this.accountObserveble = this.accountService.getAccount(accountId, this.currentPage, this.pageSize).pipe(
      catchError(err => {
        Swal.fire({
          icon: 'error',
          title: 'Account not found',
          text: 'The specified account could not be found. Please check the account ID and try again.',
        });
        this.errorAccountMessage = 'Account not found';
        return throwError(err);
      })
    );
  }
  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }
  handleAccountOperation() {
    let accountId: string;
    if(this.accountFormGroup.value.accountId){
      accountId = this.accountFormGroup.value.accountId;
    }else{
      accountId = this.account;
    }

    let operationType = this.operationFormGroup.value.operationType;
    let amount: number = this.operationFormGroup.value.amount;
    let description: string = this.operationFormGroup.value.description;
    let accountDestination: string = this.operationFormGroup.value.accountDestination;
    this.customerId = this.route.snapshot.params['id'];


    const auditLog: { idUser: number; idClient: number; details: string; operation: any; timestamp: Date } = {
      idUser: 1, // Replace with the actual user ID or get it from AuthService
      idClient: 1, // Replace with the actual client ID or get it from AuthService
      timestamp: new Date(),
      operation: operationType, // Assuming operationType is a string representing the operation
      details: `Performed ${operationType} operation on account ${accountId}`
    };
    if (operationType == 'DEBIT') {
      this.accountService.debit(accountId, amount, description).subscribe({
        next: (data) => {
          alert("Success Debit")
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'DEBIT FAILED',
            text: 'Please enter a valid balance.',
          });
          console.error('Transfer error:', error);        }
      })
    } else if (operationType == 'CREDIT') {
      this.accountService.credit(accountId, amount, description).subscribe({
        next: (data) => {
          alert("Success CREDIT")
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'CREDIT FAILED',
            text: 'Please enter a valid balance.',
          });
          console.error('Transfer error:', error);        }
      })

    } else if (operationType == 'TRANSFER') {
      this.accountService.transfer(accountId, accountDestination, amount).subscribe({
        next: (data) => {
          alert("Success TRANSFER")
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error: (error) => {
          // Error case
          Swal.fire({
            icon: 'error',
            title: 'TRANSFER FAILED',
            text: 'Please check the destination account ID or enter a valid balance.',
          });
          console.error('Transfer error:', error);
        }
      });

    }

  }

}
