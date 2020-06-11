import { IPhoto } from 'app/shared/model/photo.model';
import { ICategory } from 'app/shared/model/category.model';
import { IJob } from 'app/shared/model/job.model';

export interface IItem {
  id?: number;
  title?: string;
  description?: string;
  currentPrice?: number;
  statusItem?: boolean;
  photos?: IPhoto[];
  category?: ICategory;
  jobs?: IJob[];
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public currentPrice?: number,
    public statusItem?: boolean,
    public photos?: IPhoto[],
    public category?: ICategory,
    public jobs?: IJob[]
  ) {
    this.statusItem = this.statusItem || false;
  }
}
