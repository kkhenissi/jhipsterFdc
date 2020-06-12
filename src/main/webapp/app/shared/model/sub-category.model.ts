import { ICategory } from 'app/shared/model/category.model';
import { IProduct } from 'app/shared/model/product.model';

export interface ISubCategory {
  id?: number;
  name?: string;
  category?: ICategory;
  products?: IProduct[];
}

export class SubCategory implements ISubCategory {
  constructor(public id?: number, public name?: string, public category?: ICategory, public products?: IProduct[]) {}
}
