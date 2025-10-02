export class Rule{
  constructor(
    private id?: number,
    private text?: string,
    private lastEditAt?: Date
  ){}

  // Getterek:
  get getId(): number {
    return this.id!
  }

  get getText(): string {
    return this.text!
  }

  get getLastEditAt(): Date {
    return this.lastEditAt!
  }

  // Setterek:
  set setText(newText: string){
    this.text = newText
  }

  set setLastEditAt(newDate: Date){
    this.lastEditAt = newDate
  }
}
