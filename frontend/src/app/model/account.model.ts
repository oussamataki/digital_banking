import {Customer} from "./customer.model";

export interface AccountDetails {
  accountId:            string;
  balance:              number;
  currentPage:          number;
  totalPages:           number;
  pageSize:             number;
  createdAt: Date;
  accountOperationDTOS: AccountOperation[];


}

export interface AccountsDetails {
  id:            string;
  type:              string;
  balance:          number;
  createdAt:           Date;
  status:             number;
  customerDTO: Customer[];
  overDraft: number;



}




export interface AccountOperation {
  id:            number;
  operationDate: Date;
  amount:        number;
  type:          string;
  description:   string;
}
