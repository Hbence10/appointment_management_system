import { User } from "./user.model";

export class ReviewHistory {
  constructor(
    private id: number,
    private likeType: "like" | "dislike",
    private likeAt: Date,
    private likerUser: User
  ) { }

  // Getterek
  get getId(): number {
    return this.id;
  }

  get getLikeType(): "like" | "dislike" {
    return this.likeType
  }

  get getLikeAt(): Date {
    return this.likeAt
  }

  get getLikerUser(): User {
    return this.likerUser
  }

  // Setterek
  set setId(newId: number) {
    this.id = newId;
  }

  set setLikeType(newLikeType: "like" | "dislike") {
    this.likeType = newLikeType
  }

  set setLikeAt(newDate: Date) {
    this.likeAt = newDate
  }

  set setLikerUser(newUser : User) {
    this.likerUser = newUser
  }
}
