import { User } from "./user.model";

export class ReviewHistory{
  constructor(
    private _id:number,
    private _likeType: "like" | "dislike",
    private _likeAt: Date,
    private _likerUser: User
  ){}
}
