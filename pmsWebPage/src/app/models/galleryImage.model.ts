export class Gallery{
  constructor(
    private id: number,
    private photoName: string,
    private photoPath: string,
    private placement: number,
    private placeholders: string[] = ["Válaszd ki a kivánt fényképet"],
    private labelText: string[] = ["Fájl feltöltése"]
  ){}

  // Getterek:
  get getId(): number {
    return this.id;
  }

  get getName(): string {
    return this.photoName
  }

  get getPhotoPath(): string {
    return this.photoPath
  }

  get getPlacement(): number {
    return this.placement
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }

  // Setterek
  set setId(newId: number){
    this.id = newId
  }

  set setPhotoName(newName: string){
    this.photoName = newName
  }

  set setPhotoPath(newPath: string){
    this.photoPath = newPath
  }

  set setPlacement(newPlacement: number){
    this.placement = newPlacement
  }
}
