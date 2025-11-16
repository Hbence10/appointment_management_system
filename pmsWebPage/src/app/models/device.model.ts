import { DevicesCategory } from "./deviceCategory.model";

export class Device {
  constructor(
    private id: number | null = null,
    private name?: string,
    private amount?: number,
    private categoryId: DevicesCategory | null = null,
    private placeholders: string[] = ["Irjad be az eszköz nevét", "Az eszöz darabszáma", "Válaszd ki az eszköz kategóriáját"],
    private labelText: string[] = ["Eszköz neve", "Darabszám", "Eszköz kategóriája"]
  ) { }

  // Getterek:
  get getId(): number | null {
    return this.id;
  }

  get getName(): string {
    return this.name!;
  }

  get getAmount(): number {
    return this.amount!
  }

  get getCategoryId(): DevicesCategory{
    return this.categoryId!
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

  set setCategoryId(newCategory: DevicesCategory){
    this.categoryId = newCategory
  }
}
