export class Device {
  constructor(
    private id: number | null,
    private name: string,
    private amount: number,
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
    private placeholders: string[] = ["Irjad be az eszköz nevét", "Az eszöz darabszáma", "Válaszd ki az eszköz kategóriáját"],
    private labelText: string[] = ["Eszköz neve", "Darabszám", "Eszköz kategóriája"]
  ) { }

  // Getterek:
  get getId(): number | null {
    return this.id;
  }

  get getName(): string {
    return this.name;
  }

  get getAmount(): number {
    return this.amount
  }

  get getIsDeleted(): boolean {
    return this.isDeleted;
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt;
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }

  // Setterek:
  set setName(newName: string) {
    this.name = newName
  }

  set setAmount(newAmount: number) {
    this.amount = newAmount
  }
}
