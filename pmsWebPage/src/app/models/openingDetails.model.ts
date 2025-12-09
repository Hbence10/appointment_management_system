export class OpeningDetails {
  constructor(
    private dayName: "Hétfő" | "Kedd" | "Szerda" | "Csütörtök" | "Péntek" | "Szombat" | "Vasárnap",
    private startHour: number,
    private startMin: number,
    private endHour: number,
    private endMin: number,
    private id: number | null = null
  ) { }

  get getId(): number {
    return this.id!
  }

  get getDayName(): string {
    return this.dayName
  }

  get getStartHour(): number {
    return this.startHour
  }

  get getStartMin(): number {
    return this.startMin
  }

  get getEndHour(): number {
    return this.endHour
  }

  get getEndMin(): number {
    return this.endMin
  }

  set setDayName(dayName: "Hétfő" | "Kedd" | "Szerda" | "Csütörtök" | "Péntek" | "Szombat" | "Vasárnap") {
    this.dayName = dayName
  }

  set setStartHour(hour: number) {
    this.startHour = hour
  }

  set setStartMin(min: number) {
    this.startMin = min
  }

  set setEndHour(hour: number) {
    this.endHour = hour
  }

  set setEndMin(min: number) {
    this.endMin = min
  }
}
