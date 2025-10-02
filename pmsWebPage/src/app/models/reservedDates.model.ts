import { ReservedHours } from "./reservedHours.model";

export class ReservedDates {
  constructor(
    private date?: Date,
    private id: number| null = null,
    private isHoliday: boolean = false,
    private isClosed: boolean = false,
    private isFull: boolean = false,
    private reservedHours: ReservedHours[] = [],
    private unavailableHours: number[] = [],
    private availableHours: number[] = []
  ){}

  // Getterek:
  get getId(): number | null {
    return this.id
  }

  get getDate(): Date {
    return this.date!
  }

  get getIsHoliday(): boolean {
    return this.isHoliday
  }

  get getIsClosed(): boolean {
    return this.isClosed
  }

  get getIsFull(): boolean {
    return this.isFull
  }

  get getReservedHours(): (ReservedHours | any)[] {
    return this.reservedHours
  }

  get getUnavailableHours(): number[] {
    return this.unavailableHours
  }

  get getAvailableHours(): number[] {
    return this.availableHours
  }

  // Setterek
  set setUnavailableHours(newList: number[]){
    this.unavailableHours = newList
  }

  set setAvailableHours(newList: number[]){
    this.availableHours = newList
  }

  set setReservedHours(newList: ReservedHours[]){
    this.reservedHours = newList
  }

}
