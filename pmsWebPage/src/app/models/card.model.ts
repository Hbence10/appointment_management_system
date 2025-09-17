export class CardItem{
  constructor(
    private _name: string,
    private _objectType: string,
    private _object: any
  ){}

  //getterek
  get name(): string {
    return this._name
  }

  get objectType(): string {
    return this._objectType
  }

  get object(): any {
    return this._object
  }

  //setterek:
  set name(newName: string){
    this._name = newName
  }

  set objectType(newObjectType: string){
    this._objectType = newObjectType
  }

  set object(newObject: any){
    this._object = newObject
  }
}
