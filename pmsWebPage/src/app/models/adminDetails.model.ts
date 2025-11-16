export class AdminDetails {
  constructor(
    private id?: number | null,
    private firstName?: string,
    private lastName?: string,
    private email?: string,
    private phone?: string
  ) { }

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
}
