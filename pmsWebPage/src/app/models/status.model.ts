export class Status {
  constructor(
    private id: number,
    private name: string
  ) { }

  // Getterek:
  get getId(): number {
    return this.id;
  }

  get getName(): string {
    return this.name;
  }
}
