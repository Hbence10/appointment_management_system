import { Role } from "./role.model"
export class Users {

  constructor(
    private id: number | null = null,
    private username?: string,
    private email?: string,
    private pfpPath?: string,
    private role: Role = new Role(1, "ROLE_user"),
  ) { }

  // Getterek
  get getId(): number | null {
    return this.id!
  }

  get getUsername(): string {
    return this.username!
  }

  get getEmail(): string {
    return this.email!
  }

  get getPfpPath(): string {
    return this.pfpPath!
  }

  get getRole(): Role {
    return this.role!
  }

  // Setterek
  set setUsername(newUsername: string){
    this.username = newUsername
  }

  set setEmail(newEmail: string){
    this.email = newEmail
  }

  set setPfpPath(newPath: string){
    this.pfpPath = newPath
  }

  set setRole(newRole: Role){
    this.role = newRole
  }
}
