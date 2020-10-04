export interface IAccountApplication {
  id?: number;
  customerID?: number;
  initialCredit?: number;
}

export const defaultValue: Readonly<IAccountApplication> = {};
