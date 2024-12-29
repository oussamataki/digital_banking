import {Component, Input, OnInit} from '@angular/core';
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {CustomerService} from "../services/customer.service";
import {Router} from "@angular/router";
import {Agent} from "../model/agent.model";
import {CommonModule, JsonPipe} from "@angular/common";



@Component({
  selector: 'app-agent',
  standalone: true,
  imports: [
    JsonPipe,CommonModule, FormsModule, ReactiveFormsModule
  ],
  templateUrl: './agent.component.html',
  styleUrl: './agent.component.css'
})
export class AgentComponent implements OnInit{
  @Input() isDashboard: boolean = false;
  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number=5
  customers$!: Observable<Array<Customer>>;
  errorMessage!: string;
  searchFormGroup!: FormGroup | undefined;
   agent$!: Observable<Array<Customer>>;
  constructor(private authService:AuthService,private customerService: CustomerService, private formBuilder: FormBuilder,private router:Router) { }

  ngOnInit(): void {
    this.searchFormGroup = this.formBuilder.group({
      keyword: this.formBuilder.control("")
    });
    this.handleSearchcustomers();



  }


  handleSearchcustomers() {
    let kw = this.searchFormGroup?.value.keyword;
    this.agent$ = this.customerService.searchAgent(kw).pipe(
      catchError(err => {
        this.errorMessage = err.message
        return throwError(err);
      })
    );

  }
  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchcustomers();
  }
  handleDeleteCustomer(c: Customer) {
    let conf = confirm("Are you sure?");
    if (!conf) return;

    this.customerService.deleteCustomer(c.id).subscribe({
      next: (resp) => {
        this.customers$ = this.customers$.pipe(
          map(data => {
            let index = data.indexOf(c);
            data.splice(index, 1);  // Utilisez splice pour supprimer l'élément correctement
            return data;
          })
        );
      },
      error: err => {
        console.log(err);

        alert("You do not have permission to delete this customer for traceability reasons.");

      }
    });
  }

  handleEditCustomer() {
    throw new Error('Method not implemented.');
  }
  handleCustomerAccount(customer: Customer) {
    this.router.navigateByUrl("/admin/customer-accounts/"+customer.id,{state:customer});
  }

  handleUpdateCustomer(customer: Customer) {
    this.customerService.updateCustomer(customer)
    this.router.navigateByUrl("admin/edit-customer/" + customer.id);

  }
}
