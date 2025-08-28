export class ReservedDates {
  constructor(
    public id: number,
    public date:Date,
    public isHoliday: boolean,
    public isClosed: boolean
  ){}
}
