import { ReservedHours } from "./reservedHours.model";

export class ReservedDates {
  constructor(
    private _date:Date,
    private _id: number| null = null,
    private _isHoliday: boolean = false,
    private _isClosed: boolean = false,
    private _isFull: boolean = false,
    private _reservedHours: ReservedHours[] = [],
    private _unavailableHours: number[] = [],
    private _availableHours: number[] = []
  ){}

  //Getterek:
  get id(): number | null {
    return this._id
  }

  get date(): Date {
    return this._date
  }

  get isHoliday(): boolean {
    return this._isHoliday
  }

  get isClosed(): boolean {
    return this._isClosed
  }

  get isFull(): boolean {
    return this._isFull
  }

  get reservedHours(): ReservedHours[] {
    return this._reservedHours
  }

  get unavailableHours(): number[] {
    return this._unavailableHours
  }

  get availableHours(): number[] {
    return this._availableHours
  }

  set unavailableHours(newList: number[]){
    this._unavailableHours = newList
  }

  set availableHours(newList: number[]){
    this._availableHours = newList
  }


  toString(): string {
    return `
      date:${this._date.toString()}
      isHoliday:${this._isHoliday}
      isClosed:${this._isClosed}
      isFull:${this._isFull}
      reservedHours:${this._reservedHours.toString()}
      unavailableHours:${this._unavailableHours.toString()}
    `
  }
}
