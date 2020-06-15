import { Moment } from 'moment';
import { IVipoEntry } from 'app/shared/model/vipo-entry.model';

export interface IVipo {
  id?: number;
  name?: string;
  longitude?: string;
  lattitude?: string;
  createdDate?: Moment;
  userLogin?: string;
  userId?: number;
  entries?: IVipoEntry[];
  storeId?: number;
}

export class Vipo implements IVipo {
  constructor(
    public id?: number,
    public name?: string,
    public longitude?: string,
    public lattitude?: string,
    public createdDate?: Moment,
    public userLogin?: string,
    public userId?: number,
    public entries?: IVipoEntry[],
    public storeId?: number
  ) {}
}
