import { IJob } from 'app/shared/model/job.model';

export interface IItem {
  id?: number;
  title?: string;
  description?: string;
  currentPrice?: number;
  statusItem?: boolean;
  jobs?: IJob[];
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public currentPrice?: number,
    public statusItem?: boolean,
    public jobs?: IJob[]
  ) {
    this.statusItem = this.statusItem || false;
  }
}
