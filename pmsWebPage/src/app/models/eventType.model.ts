export class EventType {
  constructor(
    private id: number,
    private name: string
  ) { }

  get getId(): number {
    return this.id
  }

  get getName(): string {
    return this.name;
  }
}
