export class Role{
  constructor(
    private id:number,
    private name:string,
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
  ){}

  //getterek
  get getId(): number{
    return this.id
  }

  get getName(): string {
    return this.name
  }

  get getIsDeleted(): boolean{
    return this.isDeleted
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt
  }

  //setterek:
  set setId(newId: number){
    this.id = newId
  }

  set setName(newName: string){
    this.name = newName
  }

  set setIsDeleted(newValue: boolean){
    this.isDeleted = newValue
  }

  set setDeletedAt(newDate: Date){
    this.deletedAt = newDate
  }
}
