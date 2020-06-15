export interface IImageProperty {
  id?: number;
  label?: string;
  height?: number;
  width?: number;
  x?: number;
  y?: number;
  hVGColor?: string;
  entryId?: number;
}

export class ImageProperty implements IImageProperty {
  constructor(
    public id?: number,
    public label?: string,
    public height?: number,
    public width?: number,
    public x?: number,
    public y?: number,
    public hVGColor?: string,
    public entryId?: number
  ) {}
}
