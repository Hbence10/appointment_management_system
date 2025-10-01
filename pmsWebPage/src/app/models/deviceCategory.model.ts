import { Device } from "./device.model";

export class DevicesCategory {
  constructor(
    public id: number | null,
    public name: string,
    public devicesList: (Device | any)[],
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
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
