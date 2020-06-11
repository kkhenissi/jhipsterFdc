export interface IPhoto {
  id?: number;
  namePhoto?: string;
  descriptionPhoto?: string;
}

export class Photo implements IPhoto {
  constructor(public id?: number, public namePhoto?: string, public descriptionPhoto?: string) {}
}
