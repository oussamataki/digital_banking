export interface AuditLog {
  id: number;
  idUser: number;
  idClient: number;
  timestamp: Date;
  operation: string;

  type:string;
  details: string;
}
