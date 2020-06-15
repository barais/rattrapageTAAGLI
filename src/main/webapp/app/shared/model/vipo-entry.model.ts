import { Moment } from 'moment';
import { IImageProperty } from 'app/shared/model/image-property.model';

export interface IVipoEntry {
  id?: number;
  registerDate?: Moment;
  imageName?: string;
  props?: IImageProperty[];
  vipoId?: number;
}

export class VipoEntry implements IVipoEntry {
  constructor(
    public id?: number,
    public registerDate?: Moment,
    public imageName?: string,
    public props?: IImageProperty[],
    public vipoId?: number
  ) {}
}
