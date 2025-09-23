import { Device } from "./device.model";

export class DeviceCategory{
  constructor(
    private _id: number,
    private _name: string,
    private _devicesList: Device[],
    private _placeholders: string[] = ["Kategória neve"]
  ){}

  // Getterek:
  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get devicesList(): Device[]{
    return this._devicesList;
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }
}
