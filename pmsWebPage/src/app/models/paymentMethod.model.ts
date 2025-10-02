export class PaymentMethod {
  constructor(
    private id?: number,
    private name?: string,
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null
  ) { }

  // Getterek:
  get getId(): number {
    return this.id!;
  }

  get getName(): string {
    return this.name!;
  }

  get getIsDeleted(): boolean {
    return this.isDeleted
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt
  }

  // Setterek
  set setName(newName: string){
    this.name = newName
  }
}
