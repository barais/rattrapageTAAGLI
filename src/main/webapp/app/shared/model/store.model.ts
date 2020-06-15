export interface IStore {
  id?: number;
  name?: string;
  address?: string;
  postalCode?: string;
  city?: string;
  country?: string;
  userLogin?: string;
  userId?: number;
  vipoName?: string;
  vipoId?: number;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public postalCode?: string,
    public city?: string,
    public country?: string,
    public userLogin?: string,
    public userId?: number,
    public vipoName?: string,
    public vipoId?: number
  ) {}
}
