export class Role{
  constructor(
    private id?:number,
    private name?: "ROLE_user" | "ROLE_admin" | "ROLE_superAdmin"
  ){}

  // Getterek
  get getId(): number{
    return this.id!
  }

  get getName(): "ROLE_user" | "ROLE_admin" | "ROLE_superAdmin" {
    return this.name!
  }
}
