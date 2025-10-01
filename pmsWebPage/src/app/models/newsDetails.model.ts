export class News {
  constructor(
    private _id: number | null,
    private _title: string,
    private _text: string,
    private _bannerImgPath: string | null = null,
    private _placement?: number,
    private _createdAt?: Date,
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
    private _isExpand: boolean = this.placement == 1 ? true : false,
    private _placeholders: string[] = ["Írd ide a hír cÍmét", "Írd ide a hírnek a szövegét", "Válaszd ki a kivánt képet"],
    private _labelText: string[] = ["Hír címe", "Hír szövege", "Hírhez tartozó kép"]
  ) { }

  // Getterek:
  get id(): number | null {
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
    return this._placement!;
  }

  get createdAt(): Date {
    return this._createdAt!
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

  get labelText(): string[] {
    return this._labelText
  }
}
