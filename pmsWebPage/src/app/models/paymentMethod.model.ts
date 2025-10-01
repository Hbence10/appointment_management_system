export class PaymentMethod{
  constructor(
    private _id: number,
    private _name: string,
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null
  ){}

  // Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }
}
