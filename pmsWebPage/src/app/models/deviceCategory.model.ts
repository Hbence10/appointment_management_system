import { Device } from "./device.model";

export class DevicesCategory {
  constructor(
    private id: number | null = null,
    private name?: string,
    private devicesList?: Device[],
    private placeholders: string[] = ["Ird ide a kategória nevét"],
    private labelText: string[] = ["Kategória neve"]
  ) { }

  // Getterek:
  get getId(): number | null {
    return this.id;
  }

  get getName(): string {
    return this.name!;
  }

  get getDevicesList(): Device[] {
    return this.devicesList!;
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }

  // Setterek
  set setName(newName: string){
    this.name = newName
  }

  set setDevicesList(newList: Device[]){
    this.devicesList = newList
  }
}
