import { PaymentMethod } from "./paymentMethod.model";
import { ReservationType } from "./reservationType.model";
import { ReservedHours } from "./reservedHours.model";
import { Status } from "./status.model";
import { User } from "./user.model";

export class Reservation {

  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phone?: string,
    public comment?: string | null,
    public reservedAt?: Date,
    public isCanceled: boolean = false,
    public canceledAt: Date | null = null,
    public canceledBy: User | null = null,
    public user: User | null = null,
    public reservationTypeId?: ReservationType,
    public paymentMethod?: PaymentMethod,
    public status?: Status,
    public reservedHours?: ReservedHours,
  ) { }

}
