import { Role } from "./role.model"
export class User {

  constructor(
    private _username?: string,
    private _email?: string,
    private _pfpPath?: string,
    private _role?: Role,
    private _id: number | null = null,
    private _password?: string
  ) { }

  // Getterek
  get id(): number | null {
    return this._id!
  }

  get username(): string {
    return this._username!
  }

  get password(): string {
    return this._password!
  }

  get email(): string {
    return this._email!
  }

  get pfpPath(): string {
    return this._pfpPath!
  }

  get role(): Role {
    return this._role!
  }

  // Setterek
}
