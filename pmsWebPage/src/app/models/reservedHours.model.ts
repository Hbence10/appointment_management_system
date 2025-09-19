import { ReservedDates } from "./reservedDates.model"

export class ReservedHours{
  constructor(
    private id: number | null = null,
    private start?: number,
    private end?: number,
    private date?: ReservedDates
  ){}

  //Getterek:
  get getId(): number|null {
    return this.id!
  }

  get getStart(): number{
    return this.start!
  }

  get getEnd(): number{
    return this.end!
  }

  get getDate(): ReservedDates{
    return this.date!
  }

  //Setterek:
  set setStart(newStart: number){
    this.start = newStart
  }

  set setEnd(newEnd: number){
    this.end = newEnd
  }

  set setDate(newDate: ReservedDates){
    this.date = newDate
  }
}
