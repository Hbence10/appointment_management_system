export class CloseReason{
  constructor(
    private name?: string,
    private id:number | null = null,
    private isDeleted: boolean = false,
    private deletedAt?: Date
  ){}

  // Getterek
  get getId(): number{
    return this.id!
  }

  get getName(): string {
    return this.name!
  }

  get getIsDeleted(): boolean{
    return this.isDeleted!
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt!
  }

  // Setterek:
  set setName(newName: string){
    this.name = newName
  }
}
