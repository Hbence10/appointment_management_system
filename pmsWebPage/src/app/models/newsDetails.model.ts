export class NewsDetails {
  constructor(
    private _id: number,
    private _title: string,
    private _text: string,
    private _bannerImgPath: string | null,
    private _placement: number,
    private _createdAt: Date,
    private _isExpand: boolean = this.placement == 1 ? true : false,
    private _placeholders: string[] = ["Hír címe", "Hír szövege", "Hírhez tartozó szöveg"]
  ) { }

  // Getterek:
  get id(): number {
    return this._id;
  }

  get title(): string {
    return this._title;
  }

  get text(): string {
    return this._text;
  }

  get bannerImgPath(): string | null {
    return this._bannerImgPath;
  }

  get placement(): number {
    return this._placement;
  }

  get createdAt(): Date {
    return this._createdAt
  }

  get isExpand(): boolean {
    return this._isExpand
  }

  set isExpand(newState: boolean) {
    this._isExpand = newState
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }
}
