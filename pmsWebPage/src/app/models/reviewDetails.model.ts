import { ReviewHistory } from "./reviewHistory.model"
import { Users } from "./user.model"

export class Review {

  constructor(
    private id: number | null = null,
    private reviewText?: string,
    private rating?: number,
    private author?: Users,
    private isAnonymus: boolean = false,
    private createdAt: Date = new Date(),
    private isDeleted: boolean = false,
    private deletedAt: Date | null = null,
    private likeCount: number = 0,
    private dislikeCount: number = 0,
    private likeHistories: ReviewHistory[] = []
  ) {}

  // Getterek:
  get getId(): number | null {
    return this.id!
  }

  get getAuthor(): Users {
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

  set setAuthor(newAuthor: Users) {
    this.author = newAuthor
  }

  set setLikeHistory(newList: ReviewHistory[]){
    this.likeHistories = newList
  }
}
