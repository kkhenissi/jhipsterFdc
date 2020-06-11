import { IItem } from 'app/shared/model/item.model';

export interface IPhoto {
  id?: number;
  namePhoto?: string;
  descriptionPhoto?: string;
  item?: IItem;
}

export class Photo implements IPhoto {
  constructor(public id?: number, public namePhoto?: string, public descriptionPhoto?: string, public item?: IItem) {}
}
