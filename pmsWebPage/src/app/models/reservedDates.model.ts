import { ReservedHours } from "./reservedHours.model";

export class ReservedDates {
  constructor(
    public id: number,
    public date:Date,
    public isHoliday: boolean = false,
    public isClosed: boolean = false,
    public isFull: boolean = false,
    public reservedHours: ReservedHours[] = []
  ){}
}
