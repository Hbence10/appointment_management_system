import { User } from "./user.model";

export class ReviewHistory {
  constructor(
    private _id: number,
    private _likeType: "like" | "dislike",
    private _likeAt: Date,
    private _likerUser: User
  ) { }

  //Getterek
  get likeType(): "like" | "dislike" {
    return this._likeType
  }

  get likerUser(): User {
    return this._likerUser
  }
  //Setterek
}
