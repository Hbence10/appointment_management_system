import { Device } from "./device.model";

export class DeviceCategory{
  constructor(
    public id: number,
    public name: string,
    public devicesList: Device[]
  ){}
}
