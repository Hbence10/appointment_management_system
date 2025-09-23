export class Device {
  constructor(
    private _id: number,
    private _name: string,
    private _amount: number,
    private _placeholders: string[] = ["Irjad be az eszköz nevét", "Az eszöz darabszáma", "Válaszd ki az eszköz kategóriáját"],
    private _labelText: string[] = ["Eszköz neve", "Darabszám", "Eszköz kategóriája"]
  ) { }

  // Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get amount(): number {
    return this._amount
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }

  get labelText(): string[] {
    return this._labelText
  }
}
