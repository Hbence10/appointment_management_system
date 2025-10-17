import { DevicesCategory } from "../deviceCategory.model"

export class Details {

  constructor(
    private _title: string,
    private _buttonText: "newEntity" | "saveChanges" | "deleteEntity" | "galleryView" | "cancelReservation" | "",
    private _objectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "reservation" | "user",
    private _deviceCategory?: DevicesCategory
  ) { }

  //getterek:
  get title(): string {
    return this._title
  }

  get buttonText(): "newEntity" | "saveChanges" | "deleteEntity" | "galleryView" | "cancelReservation" | "" {
    return this._buttonText
  }

  get objectType(): "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "reservation" | "user" {
    return this._objectType
  }

  get deviceCategory(): DevicesCategory {
    return this._deviceCategory!
  }

  //setterek:
  set title(newTitle: string) {
    this._title = newTitle
  }

  set buttonText(newButtonText: "newEntity" | "saveChanges" | "deleteEntity" | "galleryView" | "cancelReservation" | "") {
    this._buttonText = newButtonText
  }

  set objectType(newObjectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "reservation" | "user") {
    this._objectType = newObjectType
  }
}
