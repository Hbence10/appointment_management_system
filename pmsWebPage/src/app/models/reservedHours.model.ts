import { ReservedDates } from "./reservedDates.model"

export class ReservedHours {
  constructor(
    private _id?: number,
    private _start?: number,
    private _end?: number,
    private _date: ReservedDates = new ReservedDates()
  ) { }

  //Getterek:
  get id(): number {
    return this._id!
  }

  get start() {
    return this._start!
  }

  get end() {
    return this._end!
  }

  get date(): ReservedDates {
    return this._date!
  }

  //Setterek:
  set id(newId: number) {
    this._id = newId
  }

  set start(newStart: number) {
    this._start = newStart
  }

  set end(newEnd: number) {
    this._end = newEnd
  }

  set date(newDate: ReservedDates){
    this._date = newDate
  }
}
