import { IItem } from 'app/shared/model/item.model';
import { IFdcUser } from 'app/shared/model/fdc-user.model';

export interface IJob {
  id?: number;
  jobTitle?: string;
  minSalary?: number;
  maxSalary?: number;
  items?: IItem[];
  fdcUser?: IFdcUser;
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public jobTitle?: string,
    public minSalary?: number,
    public maxSalary?: number,
    public items?: IItem[],
    public fdcUser?: IFdcUser
  ) {}
}
