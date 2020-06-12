import { ISubCategory } from 'app/shared/model/sub-category.model';
import { CategoryStatus } from 'app/shared/model/enumerations/category-status.model';

export interface ICategory {
  id?: number;
  name?: string;
  status?: CategoryStatus;
  subcategories?: ISubCategory[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public status?: CategoryStatus, public subcategories?: ISubCategory[]) {}
}
