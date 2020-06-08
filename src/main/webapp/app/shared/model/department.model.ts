import { ILocation } from 'app/shared/model/location.model';
import { IFdcUser } from 'app/shared/model/fdc-user.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  location?: ILocation;
  fdcUsers?: IFdcUser[];
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentName?: string, public location?: ILocation, public fdcUsers?: IFdcUser[]) {}
}
