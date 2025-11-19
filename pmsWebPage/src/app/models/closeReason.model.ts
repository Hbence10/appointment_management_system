import { Users } from "./user.model"

export class CloseReason {
  constructor(
    private name?: string,
    private creatorUser?: Users,
    private id: number | null = null,
  ) { }

  // Getterek
  get getId(): number {
    return this.id!
  }

  get getName(): string {
    return this.name!
  }

  // Setterek:
  set setName(newName: string) {
    this.name = newName
  }
}
