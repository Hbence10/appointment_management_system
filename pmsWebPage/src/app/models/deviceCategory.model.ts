import { Device } from "./device.model";

export class DeviceCategory {
  constructor(
    private _id: number | null,
    private _name: string,
    private _devicesList: Device[],
    private _placeholders: string[] = ["Ird ide a kategória nevét"],
    private _labelText: string[] = ["Kategória neve"]
  ) { }

  // Getterek:
  get id(): number | null {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get devicesList(): Device[] {
    return this._devicesList;
  }

  get placeholdersText(): string[] {
    return this._placeholders
  }

  get labelText(): string[] {
    return this._labelText
  }
}
