export class ReservationType {
  constructor(
    private _id: number | null,
    private _name: string = "",
    private _price: number | null,
    private _placeholders: string[] = ["Írd ide a szolgáltatás nevét", "Add meg az árát"],
    private _labelText: string[] = ["Szolgáltatás neve", "Árazás"]
  ) { }

  //Getterek:
  get id(): number | null {
    return this._id;
  }

  get name(): string {
    return this._name
  }

  get price(): number | null {
    return this._price
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }

  get labelText(): string[] {
    return this._labelText
  }
}
