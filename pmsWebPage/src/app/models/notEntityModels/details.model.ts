export class Details {

  constructor(
    private _title: string,
    private _buttonText: "newEntity" | "saveUpdate" | "deleteEntity" | "galleryView" | "cancelReservation" | "",
    private _objectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "reservation",
    private _deviceCategory?: string
  ) { }

  //getterek:
  get title(): string {
    return this._title
  }

  get buttonText(): "newEntity" | "saveUpdate" | "deleteEntity" | "galleryView" | "cancelReservation" | "" {
    return this._buttonText
  }

  get objectType(): "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "reservation" {
    return this._objectType
  }

  get deviceCategory(): string | undefined {
    return this._deviceCategory
  }

  //setterek:
  set title(newTitle: string) {
    this._title = newTitle
  }

  set buttonText(newButtonText: "newEntity" | "saveUpdate" | "deleteEntity" | "galleryView" | "cancelReservation" | "") {
    this._buttonText = newButtonText
  }

  set objectType(newObjectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "reservation") {
    this._objectType = newObjectType
  }
}
