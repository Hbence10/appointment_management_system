export class GalleryImage{
  constructor(
    private _id: number,
    private _photoName: string,
    private _photoPath: string,
    private _placement: number
  ){}

  // Getterek:
  get id(): number {
    return this._id;
  }

  get photoName(): string {
    return this._photoName
  }

  get photoPath(): string {
    return this._photoPath
  }

  get placement(): number {
    return this._placement
  }
}
