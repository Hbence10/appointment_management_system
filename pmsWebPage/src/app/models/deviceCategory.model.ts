import { Device } from "./device.model";

export class DeviceCategory{
  constructor(
    private _id: number,
    private _name: string,
    private _devicesList: Device[]
  ){}

  // Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get deviceList(): Device[]{
    return this._devicesList;
  }
}
