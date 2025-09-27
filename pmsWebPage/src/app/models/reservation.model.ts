import { PaymentMethod } from "./paymentMethod.model";
import { ReservationType } from "./reservationType.model";
import { ReservedHours } from "./reservedHours.model";
import { Status } from "./status.model";
import { User } from "./user.model";

export class Reservation {

  constructor(
    private firstName?: string,
    private lastName?: string,
    private email?: string,
    private phone?: string,
    private reservedAt?: string,
    private id: number | null = null,
    private comment: string | null = null,
    private isCanceled: boolean = false,
    private canceledAt: Date | null = null,
    private canceledBy: User | null = null,
    private user: User | null = null,
    private reservationTypeId?: ReservationType,
    private paymentMethod?: PaymentMethod,
    private status: Status = new Status(1, "Aktív"),
    private reservedHours: ReservedHours = new ReservedHours(),
    private phoneCountryCode: {id: number, countryCode: number, countryName: string} | null = null
  ) { }

  // Getterek:
  get getId(): number {
    return this.id!;
  }

  get getFirstName(): string {
    return this.firstName!;
  }

  get getLastName(): string {
    return this.lastName!;
  }

  get getEmail(): string {
    return this.email!;
  }

  get getPhone(): string {
    return this.phone!;
  }

  get getComment(): string | null {
    return this.comment;
  }

  get getReservedAt(): string {
    return this.reservedAt!;
  }

  get getIsCanceled(): boolean {
    return this.isCanceled
  }

  get getCanceledAt(): Date | null {
    return this.canceledAt;
  }

  get getCanceledBy(): User | null {
    return this.canceledBy
  }

  get getUser(): User | null {
    return this.user
  }

  get getReservationTypeId(): ReservationType {
    return this.reservationTypeId!
  }

  get getPaymentMethod(): PaymentMethod {
    return this.paymentMethod!;
  }

  get getStatus(): Status {
    return this.status!;
  }

  get getReservedHours(): ReservedHours {
    return this.reservedHours!;
  }

  get getPhoneCode(): {id: number, countryCode: number, countryName: string} | null{
    return this.phoneCountryCode;
  }

  //Setterek:
  set setFirstName(newFirstName: string) {
    this.firstName = newFirstName;
  }

  set setLastName(newLastName: string) {
    this.lastName = newLastName;
  }

  set setEmail(newEmail: string) {
    this.email = newEmail;
  }

  set setPhone(newPhone: string) {
    this.phone = newPhone;
  }

  set setComment(newComment: string) {
    this.comment = newComment;
  }

  set setReservedAt(newDate: string){
    this.reservedAt = newDate
  }

  set setIsCanceled(newValue: boolean){
    this.isCanceled = newValue
  }

  set setCanceledAt(newDate: Date){
    this.canceledAt = newDate
  }

  set setCanceledBy(newUser: User){
    this.canceledBy = newUser
  }

  set setUser(newUser: User){
    this.user = newUser
  }

  set setReservationTypeId(newReservationType: ReservationType){
    this.reservationTypeId = newReservationType
  }

  set setPaymentMethod(newPaymentMethod: PaymentMethod){
    this.paymentMethod = newPaymentMethod
  }

  set setStatus(newStatus: Status){
    this.status = newStatus
  }

  set setReservedHours(newReservedHours: ReservedHours){
    this.reservedHours = newReservedHours
  }

  set setPhoneCode(newPhoneCode: {id: number, countryCode: number, countryName: string}){
    this.phoneCountryCode = newPhoneCode
  }
}
