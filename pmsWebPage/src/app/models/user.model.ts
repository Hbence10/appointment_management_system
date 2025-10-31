import { AdminDetails } from "./adminDetails.model"
import { Role } from "./role.model"
export class Users {

  constructor(
    private id: number | null = null,
    private username?: string,
    private password?: string,
    private email?: string,
    private isNotificationAboutNews: boolean = false,
    private pfpPath: string = "assets/placeholder.png",
    private role: Role = new Role(1, "ROLE_user"),
    private adminDetails: AdminDetails | null = null,
    private placeholders: string[] = ["Add meg az első nevét az adminnak", "Add meg a második nevét az adminnak", "Add meg az email címét az adminnak", "Add meg a telefonszámát az adminnak"],
    private labelText: string[] = ["Első név", "Második név", "Email cím", "Telefonszám"]
  ) { }

  // Getterek
  get getId(): number | null {
    return this.id!
  }

  get getName(): string {
    return this.username!
  }

  get getEmail(): string {
    return this.email!
  }

  get getPfpPath(): string {
    return this.pfpPath!
  }

  get getIsNotificationAboutNews(): boolean {
    return this.isNotificationAboutNews
  }

  get getRole(): Role {
    return this.role!
  }

  get getAdminDetails(): AdminDetails {
    return this.adminDetails!
  }

  get getPlaceholdersText(): string[] {
    return this.placeholders
  }

  get getLabelText(): string[] {
    return this.labelText
  }

  // Setterek
  set setUsername(newUsername: string) {
    this.username = newUsername
  }

  set setEmail(newEmail: string) {
    this.email = newEmail
  }

  set setPfpPath(newPath: string) {
    this.pfpPath = newPath
  }

  set setRole(newRole: Role) {
    this.role = newRole
  }

  set setAdminDetails(newDetails: AdminDetails){
    this.adminDetails = newDetails
  }
}
