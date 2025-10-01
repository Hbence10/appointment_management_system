import { Device } from "./device.model";

export class DevicesCategory {
  constructor(
    private id: number | null,
    private name: string,
    private devicesList: (Device | any)[],
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

  get getIsDeleted(): boolean{
    return this.isDeleted
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }

  // Setterek
  set setId(newId: number){
    this.id = newId
  }

  set setName(newName: string){
    this.name = newName
  }

  set setDevicesList(newList: Device[]){
    this.devicesList = newList
  }

  set setIsDeleted(newValue: boolean){
    this.isDeleted = newValue
  }

  set setDeletedAt(newDate: Date){
    this.deletedAt = newDate
  }
}
