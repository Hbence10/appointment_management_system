import { Device } from "./device.model";

export class DeviceCategory {
  constructor(
    private id: number | null,
    private name: string,
    private devicesList: (Device | any)[],
    private placeholders: string[] = ["Ird ide a kategória nevét"],
    private labelText: string[] = ["Kategória neve"]
  ) { }

  // Getterek:
  get getId(): number | null {
    return this.id;
  }

  get getName(): string {
    return this.name;
  }

  get getDevicesList(): (Device | any)[] {
    return this.devicesList;
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }
}
