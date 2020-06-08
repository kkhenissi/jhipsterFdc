import { Moment } from 'moment';
import { IJob } from 'app/shared/model/job.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface IFdcUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  jobs?: IJob[];
  manager?: IFdcUser;
  department?: IDepartment;
}

export class FdcUser implements IFdcUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public jobs?: IJob[],
    public manager?: IFdcUser,
    public department?: IDepartment
  ) {}
}
