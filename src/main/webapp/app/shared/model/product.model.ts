export interface IProduct {
  id?: number;
  nameProduct?: string;
  descriptionProduct?: string;
  priceProduct?: number;
}

export class Product implements IProduct {
  constructor(public id?: number, public nameProduct?: string, public descriptionProduct?: string, public priceProduct?: number) {}
}
