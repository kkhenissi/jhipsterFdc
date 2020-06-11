import { Moment } from 'moment';

export interface IUserFdc {
  id?: number;
  userNameUser?: string;
  firstNameUser?: string;
  lastNameUser?: string;
  emailUser?: string;
  passwordUser?: string;
  dateRegistrationUser?: Moment;
  avatarUser?: string;
  statusUser?: boolean;
}

export class UserFdc implements IUserFdc {
  constructor(
    public id?: number,
    public userNameUser?: string,
    public firstNameUser?: string,
    public lastNameUser?: string,
    public emailUser?: string,
    public passwordUser?: string,
    public dateRegistrationUser?: Moment,
    public avatarUser?: string,
    public statusUser?: boolean
  ) {
    this.statusUser = this.statusUser || false;
  }
}
