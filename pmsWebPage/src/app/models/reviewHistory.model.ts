import { User } from "./user.model";

export class ReviewHistory {
  constructor(
    private _id: number,
    private _likeType: "like" | "dislike",
    private _likeAt: Date,
    private _likerUser: User
  ) { }

  // Getterek
  get id(): number {
    return this._id;
  }

  get likeType(): "like" | "dislike" {
    return this._likeType
  }

  get likeAt(): Date {
    return this._likeAt
  }

  get likerUser(): User {
    return this._likerUser
  }

  // Setterek
  set id(newId: number) {
    this._id = newId;
  }

  set likeType(newLikeType: "like" | "dislike") {
    this._likeType = newLikeType
  }

  set likeAt(newDate: Date) {
    this._likeAt = newDate
  }

  set likerUser(newUser : User) {
    this._likerUser = newUser
  }
}
