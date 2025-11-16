export class ReservationType {
  constructor(
    private id: number | null = null,
    private name?: string,
    private price?: number,
    private placeholders: string[] = ["Írd ide a szolgáltatás nevét", "Add meg az árát"],
    private labelText: string[] = ["Szolgáltatás neve", "Árazás"]
  ) { }

  // Getterek:
  get getId(): number | null {
    return this.id;
  }

  get getName(): string {
    return this.name!
  }

  get getPrice(): number | null {
    return this.price!
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

  set setPrice(newPrice: number) {
    this.price = newPrice
  }
}
