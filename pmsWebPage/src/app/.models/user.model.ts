export class User {
  constructor(
    public id: number,
    public username: string,
    public email: string,
    public pfpPath: string,
    public role: "user" | "admin" | "superAdmin",
  ) { }
}
