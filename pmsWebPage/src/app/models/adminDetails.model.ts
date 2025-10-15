export class AdminDetails {
  constructor(
    private firstName?: string,
    private lastName?: string,
    private email?: string,
    private phone?: string,
    private id?: number,
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null
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

  get getIsDeleted(): boolean {
    return this.isDeleted;
  }

  get getDeletedAt(): Date{
    return this.deletedAt!;
  }
}
