export class PaymentMethod{
  constructor(
    private _id: number,
    private _name: string
  ){}

  // Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }
}
