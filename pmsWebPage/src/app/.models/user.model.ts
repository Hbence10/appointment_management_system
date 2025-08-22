export class User{
  constructor(
    private id:number,
    private username:string,
    private email:string,
    private pfpPath:string,
    private role: "user" | "admin" | "superAdmin",
  ){}
}
