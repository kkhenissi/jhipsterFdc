export interface IProductItem {
  id?: number;
  quantityItem?: number;
}

export class ProductItem implements IProductItem {
  constructor(public id?: number, public quantityItem?: number) {}
}
