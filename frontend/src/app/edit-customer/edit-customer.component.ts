import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CustomerService} from "../services/customer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../model/customer.model";
import Swal from 'sweetalert2';
import {NgIf} from "@angular/common";
import {AppUser} from "../model/appUser.model";
import {HttpClient} from "@angular/common/http";
import {AngularFireStorage, AngularFireUploadTask} from "@angular/fire/compat/storage";
import {finalize, Observable} from "rxjs";
import {AngularFireDatabase} from "@angular/fire/compat/database";
import {uploadBytesResumable, ref, Storage, getStorage, provideStorage} from "@angular/fire/storage";
import {environment} from "../../environments/environment";
import firebase from "firebase/compat";
import initializeApp = firebase.initializeApp;
import {provideFirebaseApp} from "@angular/fire/app";




@Component({
  selector: 'app-edit-customer',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
  ],
  templateUrl: './edit-customer.component.html',
  styleUrl: './edit-customer.component.css'
})
export class EditCustomerComponent implements OnInit{
  updateCustomerFormGroup!: FormGroup;
  imageUrl: string | null = null;
  selectedFile: File | null = null;

  constructor(private fb: FormBuilder, private serviceCustomer: CustomerService, private router: ActivatedRoute,
              private routerNav: Router,private http: HttpClient) {
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
    this.uploadImage
  }

  uploadImage() {
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('image', this.selectedFile);

      this.http.post('http://localhost:3000/upload', formData).subscribe(
        (response: any) => {
          // Assuming the server responds with the URL of the uploaded image
          this.imageUrl = response.imageUrl;
          console.log('Image uploaded successfully:', response);
        },
        (error) => {
          console.error('Error uploading image:', error);
        }
      );
    }
  }
  ngOnInit(): void {
    this.updateCustomerFormGroup = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(4)]],
      surName: [null,  [Validators.required, Validators.minLength(4)]],
      email: [null, [Validators.required,,Validators.email]],
      image: [null],
      adresse:[null],
      phoneNumber:[null],
      id: [null],
    });
    this.getCustomer();
  }

  updateCustomer() {
    let customer: Customer = this.updateCustomerFormGroup.value;
    customer.id = this.router.snapshot.params['id'];
    this.serviceCustomer.getCustomerById(customer.id).subscribe((e)=>{
     customer.appUser= e.appUser;
     customer.appUser.password;
     customer.timestamp = e.timestamp;

      this.serviceCustomer.updateCustomer(customer).subscribe(
        {
          next: data => {
            Swal.fire({
              position: 'center',
              icon: 'success',
              title: "The customer has been successfully updated !",
              showConfirmButton: false,
              timer: 1500
            });
            this.goBack();
          },
          error: err => {
            // Gérer les erreurs ici
          }
        }
      );
      console.log("heere"+JSON.stringify(customer.appUser)+"heeere"+customer.timestamp);
    });
    console.log("heeere"+JSON.stringify(customer));

  }

  private getCustomer() {
    this.serviceCustomer.getCustomerById(this.router.snapshot.params['id']).subscribe(
      {
        next: data => {
          let customer: Customer = data;
          console.log(customer.image);
          this.updateCustomerFormGroup.patchValue({
            email: customer.email,
            name: customer.name,
            surName : customer.surName,
            password:'',
            phoneNumber:customer.phoneNumber,
            image:customer.image,
            adresse:customer.adresse,
            id: customer.id,
          });
        },
        error: err => {
          // Gérer les erreurs ici
        }
      }
    );
  }

  goBack() {
    this.routerNav.navigate(['/admin/customers']);

  }
}
