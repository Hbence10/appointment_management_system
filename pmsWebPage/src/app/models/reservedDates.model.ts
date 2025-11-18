import { CloseReason } from "./closeReason.model";
import { ReservedHours } from "./reservedHours.model";

export class ReservedDates {
  constructor(
    private date?: Date,
    private isClosed: boolean = false,
    private closeReason: CloseReason | null = null,
    private id: number| null = null,
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

  get getReservedHours(): (ReservedHours | any)[] {
    return this.reservedHours
  }

  get getUnavailableHours(): number[] {
    return this.unavailableHours
  }

  get getAvailableHours(): number[] {
    return this.availableHours
  }

  get getIsClosed(): boolean | null {
    return this.isClosed
  }

  get getCloseReason(): CloseReason | null {
    return this.closeReason
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
