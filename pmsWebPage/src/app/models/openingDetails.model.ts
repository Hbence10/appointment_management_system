export class OpeningDetails {
  constructor(
    private dayName?: "Hétfő" | "Kedd" | "Szerda" | "Csütörtök" | "Péntek" | "Szombat" | "Vasárnap",
    private startTime?: Date,
    private endTime?: Date,
    private id: number | null = null
  ) { }

  get getId(): number {
    return this.id!
  }

  get getDayName(): string {
    return this.dayName
    return this.dayName!
  }

  get getStartTime(): Date {
    return this.startTime!
  }

  get getEndTime(): Date {
    return this.endTime!
  }

  //Setterek
  set setStartTime(newStart: Date) {
    this.startTime = newStart
  }

  set setEndTime(newEnd: Date) {
    this.endTime = newEnd
  }
}
