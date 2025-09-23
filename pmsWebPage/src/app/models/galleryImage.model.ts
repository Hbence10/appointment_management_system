export class GalleryImage{
  constructor(
    private _id: number,
    private _photoName: string,
    private _photoPath: string,
    private _placement: number,
    private _placeholders: string[] = ["Válaszd ki a kivánt fényképet"],
    private _labelText: string[] = ["Fájl feltöltése"]
  ){}

  // Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._photoName
  }

  get photoPath(): string {
    return this._photoPath
  }

  get placement(): number {
    return this._placement
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }

  get labelText(): string[] {
    return this._labelText
  }
}
