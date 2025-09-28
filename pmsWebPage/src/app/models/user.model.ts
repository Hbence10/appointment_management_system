type role = {id: number, name: "ROLE_user" | "ROLE_admin" | "ROLE_superAdmin"}
export class User {

  constructor(
    private _username: string,
    private _email: string,
    private _pfpPath: string,
    private _role: role,
    private _id: number | null = null,
  ) { }

  //
  get id(): number | null {
    return this._id
  }

  get username(): string {
    return this._username
  }

  get email(): string {
    return this._email
  }

  get pfpPath(): string {
    return this._pfpPath
  }

  get role(): role {
    return this._role
  }

  //
}
