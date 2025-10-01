import { ReviewHistory } from "./reviewHistory.model"
import { User } from "./user.model"

export class Review {

  constructor(
    private _id: number | null,
    private _reviewText: string,
    private _rating: number,
    private _author: User,
    private _isAnonymus: boolean = false,
    private _createdAt: Date = new Date(),
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
    private _likeCount: number = 0,
    private _dislikeCount: number = 0,
    private _likeHistories: ReviewHistory[] = []
  ) {
    this._likeCount = this._likeHistories.filter(element => element.likeType == "like").length
    this._dislikeCount = this._likeHistories.filter(element => element.likeType == "dislike").length
  }

  // Getterek:
  get id(): number | null {
    return this._id
  }

  get author(): User {
    return this._author
  }

  get reviewText(): string {
    return this._reviewText
  }

  get rating(): number {
    return this._rating
  }

  get likeCount(): number {
    return this._likeCount
  }

  get dislikeCount(): number {
    return this._dislikeCount
  }

  get isAnonymus(): boolean {
    return this._isAnonymus
  }

  get createdAt(): Date {
    return this._createdAt
  }

  get likeHistories(): ReviewHistory[] {
    return this._likeHistories
  }

  // Setterek:
  set reviewText(newReviewText: string) {
    this._reviewText = newReviewText
  }

  set rating(newRating: number) {
    this._rating = newRating
  }

  set likeCount(newLikeCount: number) {
    this._likeCount = newLikeCount
  }

  set dislikeCount(newDislikeCount: number) {
    this._dislikeCount = newDislikeCount
  }

  set isAnonymus(newAnonymus: boolean) {
    this._isAnonymus = newAnonymus
  }

  set createdAt(newDate: Date) {
    this._createdAt = newDate
  }

  set author(newAuthor: User) {
    this._author = newAuthor
  }
}
