export class ReservationType {
  constructor(
    private _id: number,
    private _name: string = "",
    private _price: number
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
}
