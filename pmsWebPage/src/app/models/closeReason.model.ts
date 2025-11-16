export class CloseReason{
  constructor(
    private name?: string,
    private id:number | null = null
  ){}

  // Getterek
  get getId(): number{
    return this.id!
  }

  get getName(): string {
    return this.name!
  }

  // Setterek:
  set setName(newName: string){
    this.name = newName
  }
}
