import { ReviewHistory } from "./reviewHistory.model"
import { User } from "./user.model"

export class Review {

  constructor(
    private reviewText?: string,
    private rating?: number,
    private author?: User,
    private id: number | null = null,
    private isAnonymus: boolean = false,
    private createdAt: Date = new Date(),
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
    private likeCount: number = 0,
    private dislikeCount: number = 0,
    private likeHistories: ReviewHistory[] = []
  ) {
    this.likeCount = this.likeHistories.filter(element => element.getLikeType == "like").length
    this.dislikeCount = this.likeHistories.filter(element => element.getLikeType == "dislike").length
  }

  // Getterek:
  get getId(): number | null {
    return this.id!
  }

  get getAuthor(): User {
    return this.author!
  }

  get getReviewText(): string {
    return this.reviewText!
  }

  get getRating(): number {
    return this.rating!
  }

  get getLikeCount(): number {
    return this.likeCount
  }

  get getDislikeCount(): number {
    return this.dislikeCount
  }

  get getIsAnonymus(): boolean {
    return this.isAnonymus
  }

  get getCreatedAt(): Date {
    return this.createdAt
  }

  get getLikeHistories(): ReviewHistory[] {
    return this.likeHistories
  }

  get getIsDeleted(): boolean {
    return this.isDeleted;
  }

  get getDeletedAt(): Date | null {
    return this.deletedAt;
  }

  // Setterek:
  set setReviewText(newReviewText: string) {
    this.reviewText = newReviewText
  }

  set setRating(newRating: number) {
    this.rating = newRating
  }

  set setLikeCount(newLikeCount: number) {
    this.likeCount = newLikeCount
  }

  set setDislikeCount(newDislikeCount: number) {
    this.dislikeCount = newDislikeCount
  }

  set setIsAnonymus(newAnonymus: boolean) {
    this.isAnonymus = newAnonymus
  }

  set setCreatedAt(newDate: Date) {
    this.createdAt = newDate
  }

  set setAuthor(newAuthor: User) {
    this.author = newAuthor
  }

  set setIsDeleted(newValue: boolean) {
    this.isDeleted = newValue
  }

  set setDeletedAt(newDate: Date) {
    this.deletedAt = newDate
  }
}
