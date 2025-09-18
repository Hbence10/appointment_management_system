export class CardItem {
  constructor(
    private _name: string,
    private _objectType: string,
    private _object: any,
    private _button1Event: "delete" | "viewImage"
  ) { }

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

  get button1Event(): "delete" | "viewImage" {
    return this._button1Event
  }

  //setterek:
  set name(newName: string) {
    this._name = newName
  }

  set objectType(newObjectType: string) {
    this._objectType = newObjectType
  }

  set object(newObject: any) {
    this._object = newObject
  }

  set button1Event(newEvent: "delete" | "viewImage"){
    this._button1Event = newEvent
  }
}
