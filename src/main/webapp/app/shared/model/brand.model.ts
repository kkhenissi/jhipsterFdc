import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IUser } from 'app/core/user/user.model';

export interface IBrand {
  id?: number;
  name?: string;
  startDate?: Moment;
  products?: IProduct[];
  user?: IUser;
}

export class Brand implements IBrand {
  constructor(public id?: number, public name?: string, public startDate?: Moment, public products?: IProduct[], public user?: IUser) {}
}
