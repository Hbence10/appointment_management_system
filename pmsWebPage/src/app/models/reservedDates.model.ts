export class ReservedDates {
  constructor(
    public id: number,
    public date:Date,
    public startHour: number,
    public endHour: number,
    public isClosed: boolean
  ){}
}
