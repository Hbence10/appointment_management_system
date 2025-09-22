export class Details{

  constructor(
    private _title: string,
    private _buttonText: string,
    private _objectType: string
  ){}

  //getterek:
  get title(): string {
    return this._title
  }

  get buttonText(): string {
    return this._buttonText
  }

  get objectType(): string {
    return this._objectType
  }

  //setterek:
  set title(newTitle: string){
    this._title = newTitle
  }

  set buttonText(newButtonText: string){
    this._buttonText = newButtonText
  }

  set objectType(newObjectType: string){
    this._objectType = newObjectType
  }
}
