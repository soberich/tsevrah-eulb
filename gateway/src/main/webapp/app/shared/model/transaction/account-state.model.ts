import { IMonetaryTransaction } from 'app/shared/model/transaction/monetary-transaction.model';

export interface IAccountState {
  id?: number;
  customerID?: number;
  balance?: number;
  transactions?: IMonetaryTransaction[];
}

export const defaultValue: Readonly<IAccountState> = {};
