import { PaymentMethod } from "./paymentMethod.model";
import { ReservationType } from "./reservationType.model";
import { ReservedHours } from "./reservedHours.model";
import { Status } from "./status.model";
import { User } from "./user.model";

export class Reservation {

  constructor(
    private _id?: number,
    private _firstName?: string,
    private _lastName?: string,
    private _email?: string,
    private _phone?: string,
    private _comment: string | null = null,
    private _reservedAt?: Date,
    private _isCanceled: boolean = false,
    private _canceledAt: Date | null = null,
    private _canceledBy: User | null = null,
    private _user: User | null = null,
    private _reservationTypeId?: ReservationType,
    private _paymentMethod?: PaymentMethod,
    private _status?: Status,
    private _reservedHours?: ReservedHours,
  ) { }

  // Getterek:
  get id(): number {
    return this._id!;
  }

  get firstName(): string {
    return this._firstName!;
  }

  get lastName(): string {
    return this._lastName!;
  }

  get email(): string {
    return this._email!;
  }

  get phone(): string {
    return this._phone!;
  }

  get comment(): string | null {
    return this._comment;
  }

  get reservedAt(): Date {
    return this._reservedAt!;
  }

  get isCanceled(): boolean {
    return this._isCanceled
  }

  get canceledAt(): Date | null {
    return this._canceledAt;
  }

  get canceledBy(): User | null {
    return this._canceledBy
  }

  get user(): User | null {
    return this._user
  }

  get reservationTypeId(): ReservationType {
    return this._reservationTypeId!
  }

  get paymentMethd(): PaymentMethod {
    return this._paymentMethod!;
  }

  get status(): Status {
    return this._status!;
  }

  get reservedHours(): ReservedHours {
    return this._reservedHours!;
  }
}
