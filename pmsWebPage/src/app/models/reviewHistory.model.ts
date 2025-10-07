import { Review } from "./reviewDetails.model";
import { Users } from "./user.model";

export class ReviewHistory {
  constructor(
    private id: number | null = null,
    private likeType?: "like" | "dislike",
    private likerUser?: Users,
    private likedReview?: Review,
    private likeAt?: Date,
  ) { }

  // Getterek
  get getId(): number {
    return this.id!;
  }

  get getLikeType(): "like" | "dislike" {
    return this.likeType!
  }

  get getLikeAt(): Date {
    return this.likeAt!
  }

  get getLikerUser(): Users {
    return this.likerUser!
  }

  // Setterek
  set setLikeType(newLikeType: "like" | "dislike") {
    this.likeType = newLikeType
  }

  set setLikeAt(newDate: Date) {
    this.likeAt = newDate
  }

  set setLikerUser(newUser : Users) {
    this.likerUser = newUser
  }
}
