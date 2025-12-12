export class Details {
  constructor(
    private id?: number,
    private address?: string,
    private phone?: string,
    private email?: string,
    private firePhone?: string
  ) { }

  get getId(): number {
    return this.id!;
  }

  get getAddress(): string {
    return this.address!
  }

  get getPhone(): string {
    return this.phone!
  }

  get getEmail(): string {
    return this.email!
  }

  get getFirePhone(): string {
    return this.firePhone!
  }

  set setAddress(newAddress: string) {
    this.address = newAddress
  }

  set setPhone(newPhone: string) {
    this.phone = newPhone
  }

  set setEmail(newEmail: string) {
    this.email = newEmail
  }

  set setFirePhone(newPhone: string) {
    this.firePhone = newPhone
  }
}
