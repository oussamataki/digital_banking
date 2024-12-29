import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {AccountDetails, AccountsDetails} from '../model/account.model';
import {AuditLog} from "../model/auditLog.model";
import {Customer} from "../model/customer.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  backendHost: string = "http://localhost:8085";
  constructor(private http: HttpClient) { }
  public getAccounts(): Observable<Array<AccountsDetails>> {
    return this.http.get<Array<AccountsDetails>>(this.backendHost +
      "/accounts");
  }
  public getAccount(accountId: string, page: number, size: number): Observable<AccountDetails> {
    return this.http.get<AccountDetails>(this.backendHost +
      "/accounts/" + accountId + "/pageOperations?page=" + page + "&size=" + size);
  }

  public getOp(accountId: string): Observable<AccountDetails> {
    return this.http.get<AccountDetails>(this.backendHost +
      "/accounts/" + accountId + "/op" );
  }



  public debit(accountId: string, amount: number, description: string) {
    let data = { accountId: accountId, amount: amount, description: description };

    return this.http.post(this.backendHost +
      "/accounts/debit", data);
  }
  public credit(accountId: string, amount: number, description: string) {
    let data = { accountId: accountId, amount: amount, description: description };
    return this.http.post(this.backendHost +
      "/accounts/credit", data);
  }
  public transfer(accountSource: string, accountDestination: string, amount: number) {
    let data = { accountSource: accountSource, accountDestination: accountDestination, amount: amount };
    return this.http.post(this.backendHost +
      "/accounts/transfer", data);
  }
  public newCurrentAccount(balance: number, overDraft: number, customerId: number) {
    let data = {balance, overDraft, customerId}
    return this.http.post(this.backendHost+"/customers/" + customerId +"/current-accounts?overDraft=" + overDraft + "&initialBalance=" + balance , data);
  }
  public newSavingAccount(balance: number, interestRate: number, customerId: number) {
    let data = {balance, interestRate, customerId}
    return this.http.post(this.backendHost+"/customers/" + customerId +"/saving-accounts?interestRate=" + interestRate + "&initialBalance=" + balance , data);
  }

  public saveAudit(auditLog: AuditLog) {

    return this.http.post(this.backendHost+"/log/",auditLog);
  }

  public getAudit(): Observable<Array<AuditLog>> {
    return this.http.get<Array<AuditLog>>(this.backendHost +
      "/log");
  }
  public searchLog(id:number): Observable<Array<AuditLog>> {
    console.log("dkhal")
    return this.http.get<Array<AuditLog>>(this.backendHost+"/log/search?id=" + id)
  }

}
