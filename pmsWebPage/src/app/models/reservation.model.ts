import { ReservedHours } from "./reservedHours.model";

export class Reservation {

  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public comment?: string,
    public reservedAt?: Date,
    public reservationTypeId?: string,
    public reservedDate?: Date,
    public reservedHours?: ReservedHours,
    public paymentMethod?: string,
    public status?: string,
  ) { }

  setFirstName(){

  }
}
