import { Customer } from './../model/customer.model';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { Router } from '@angular/router';
import Swal from "sweetalert2";

@Component({
  selector: 'app-new-customer',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit {

  currentTimestamp: number;
  newCustomerFormGroup!: FormGroup;
  private password!: any;
  private title!: string;
  constructor(private fb: FormBuilder, private customerService: CustomerService,private router :Router) {
    this.currentTimestamp = new Date().getTime();
  }
  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(4)]],
      surName: [null,  [Validators.required, Validators.minLength(4)]],
      email: [null, [Validators.required,Validators.email]],
      image: [null],
      adresse:[null],
      title:[null],
      id: [null],
    });
  }

  onIn(event:any){
    this.password = event.target.value;
  }
  onI(event:any){
    console.log(event.target.value);
  }
  handleSaveCustomer() {
    let customer: Customer = this.newCustomerFormGroup.value;


    this.customerService.saveUser({id:0,customer:customer,password: this.password, roleNames: this.title, username:customer.name, email:customer.email}).subscribe({});

      console.log("heere"+JSON.stringify(customer));
  customer.appUser.password
    this.customerService.saveCustomers(customer).subscribe({
      next: data => {
        alert("Customer has been successfully saved");
        this.newCustomerFormGroup.reset();
        this.router.navigateByUrl('/admin/customers');
      },
      error: err => {
        Swal.fire({
          icon: 'error',
          title: 'CUSTOMER CREATION FAILED',
          text: 'Please enter a valid email address.',
        });
        console.log(err);
      }
    });
  }

}
