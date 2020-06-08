import { Moment } from 'moment';
import { IJob } from 'app/shared/model/job.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IFdcUser } from 'app/shared/model/fdc-user.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IJobHistory {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  language?: Language;
  job?: IJob;
  department?: IDepartment;
  fdcUser?: IFdcUser;
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public language?: Language,
    public job?: IJob,
    public department?: IDepartment,
    public fdcUser?: IFdcUser
  ) {}
}
