export interface IMasterAccount {
  id?: number;
  customerID?: number;
  initialCredit?: number;
}

export const defaultValue: Readonly<IMasterAccount> = {};
