export class ReservationType {
  constructor(
    private _id: number,
    private _name: string = "",
    private _price: number,
    private _placeholders: string[] = ["Szolgáltatás neve", "Árazás"]
  ) { }

  //Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name
  }

  get price(): number {
    return this._price
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }
}
