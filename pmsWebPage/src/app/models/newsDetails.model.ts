export class News {
  constructor(
    private id?: number | null,
    private title?: string,
    private text?: string,
    private bannerImgPath: string | null = null,
    private placement?: number,
    private createdAt: Date = new Date(),
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
    private placeholders: string[] = ["Írd ide a hír cÍmét", "Írd ide a hírnek a szövegét", "Válaszd ki a kivánt képet"],
    private labelText: string[] = ["Hír címe", "Hír szövege", "Hírhez tartozó kép"]
  ) { }

  // Getterek:
  get getId(): number | null {
    return this.id!;
  }

  get getTitle(): string {
    return this.title!;
  }

  get getText(): string {
    return this.text!;
  }

  get getBannerImgPath(): string | null {
    return this.bannerImgPath;
  }

  get getPlacement(): number {
    return this.placement!;
  }

  get getCreatedAt(): Date {
    return this.createdAt!
  }

  get getIsDeleted(): boolean {
    return this.isDeleted
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }

  // Setterek
  set setTitle(newTitle: string) {
    this.title = newTitle
  }

  set setText(newText: string) {
    this.text = newText
  }

  set setBannerImgPath(newPath: string) {
    this.bannerImgPath = newPath
  }

  set setPlacement(newPlacement: number) {
    this.placement = newPlacement
  }

  set setCreatedAt(newDate: Date){
    this.createdAt = newDate
  }
}
