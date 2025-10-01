import { Device } from "../device.model"
import { DevicesCategory } from "../deviceCategory.model"
import { GalleryImage } from "../galleryImage.model"
import { News } from "../newsDetails.model"
import { ReservationType } from "../reservationType.model"

export class CardItem {
  constructor(
    private _name: string,
    private _objectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery",
    private _object: DevicesCategory | Device | News | ReservationType | GalleryImage,
    private _button1Event: "delete" | "viewImage"
  ) { }

  //getterek
  get name(): string {
    return this._name
  }

  get objectType(): "deviceCategory" | "device" | "news" | "reservationType" | "gallery" {
    return this._objectType
  }

  get object(): DevicesCategory | Device | News | ReservationType | GalleryImage {
    return this._object
  }

  get button1Event(): "delete" | "viewImage" {
    return this._button1Event
  }

  //setterek:
  set name(newName: string) {
    this._name = newName
  }

  set objectType(newObjectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery") {
    this._objectType = newObjectType
  }

  set object(newObject: DevicesCategory | Device | News | ReservationType | GalleryImage) {
    this._object = newObject
  }

  set button1Event(newEvent: "delete" | "viewImage"){
    this._button1Event = newEvent
  }
}
