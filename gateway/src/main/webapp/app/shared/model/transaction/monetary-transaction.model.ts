export interface IMonetaryTransaction {
  id?: number;
  recipientID?: number;
  senderID?: number;
  amount?: number;
  accountCustomerID?: string;
  accountStateID?: number;
}

export const defaultValue: Readonly<IMonetaryTransaction> = {};
