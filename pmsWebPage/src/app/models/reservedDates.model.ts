import { ReservedHours } from "./reservedHours.model";

export class ReservedDates {
  constructor(
    private _id: number,
    private _date:Date,
    private _isHoliday: boolean = false,
    private _isClosed: boolean = false,
    private _isFull: boolean = false,
    private _reservedHours: ReservedHours[] = []
  ){}

  //Getterek:
  get id(): number {
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
}
