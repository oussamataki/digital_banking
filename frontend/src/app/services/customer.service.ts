import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../model/customer.model';
import {Account} from "../model/customer-accounts";
import {Agent} from "../model/agent.model";
import {AppUser} from "../model/appUser.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost:string="http://localhost:8085";
  constructor(private http: HttpClient) { }

  public getCustomers(): Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost+"/customers")
  }
  public searchCustomers(keyword:string): Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost+"/customers/search?keyword=" + keyword)
  }

  public saveCustomers(customer:Customer): Observable<Customer> {
    return this.http.post<Customer>(this.backendHost+"/customers",customer)
  }

  public saveUser(appUser: { password: any; id: number; roleNames: string; email: "string"; customer: Customer; username: "string" }): Observable<AppUser> {
    return this.http.post<AppUser>(this.backendHost+"/user",appUser)
  }
  public deleteCustomer(id:number) {
    return this.http.delete(this.backendHost+"/customers/"+id)
  }

  updateCustomer(customer: Customer): Observable<Array<Customer>> {
    return this.http.put<Array<Customer>>(this.backendHost + "/customer/" + customer.id, customer);
  }
  getCustomerById(id: number): Observable<Customer> {
    return this.http.get<Customer>(this.backendHost + "/customers/" + id);
  }
  public getAllBankAccounts(): Observable<Account[]>{
    return this.http.get<Account[]>(this.backendHost + "/accounts");
  }

  public getAccountsByCustomer(customerId: number): Observable<Account[]> {

    return  this.http.get<Account[]>(this.backendHost + "/customers/" + customerId + "/accounts");
  }

//agent
  public getAgent(): Observable<Array<Agent>> {
    return this.http.get<Array<Agent>>(this.backendHost+"/agent")
  }
  public deleteAgent(id:number) {
    return this.http.delete(this.backendHost+"/agent/"+id)
  }
  public searchAgent(keyword:string): Observable<Array<Customer>> {
    console.log("dkhal")
    return this.http.get<Array<Customer>>(this.backendHost+"/agent/search?keyword=" + keyword)
  }
  public saveAgent(agent:Agent): Observable<Agent> {
    return this.http.post<Agent>(this.backendHost+"/agent",agent)
  }
  updateAgent(agent: Agent): Observable<Array<Agent>> {
    return this.http.put<Array<Agent>>(this.backendHost + "/agent/" + agent.agentId, agent);
  }

  public  setAgent(customer:Customer,agent:Agent){
    return this.http.put<Array<Customer>>(this.backendHost + "/agent/" + agent.agentId,customer);

  }
  getAgentById(id: number): Observable<Agent> {
    return this.http.get<Agent>(this.backendHost + "/agents/" + id);
  }

  public getAccountsByAgent(agentId: number): Observable<Account[]> {

    return  this.http.get<Account[]>(this.backendHost + "/agents/" + agentId + "/accounts");
  }
}
