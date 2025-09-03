export class User {
  constructor(
    private id: number,
    private username: string,
    private email: string,
    private pfpPath: string,
    private role: "user" | "admin" | "superAdmin",
  ) { }

  get getId(): number {
    return this.id
  }

  get getUsername(): string {
    return this.username
  }

  get getEmail(): string {
    return this.email
  }

  get getPfpPath(): string {
    return this.pfpPath
  }

  get getRole(): "user" | "admin" | "superAdmin" {
    return this.role
  }
}
