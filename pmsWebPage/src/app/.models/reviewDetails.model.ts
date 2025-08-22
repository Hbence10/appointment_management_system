export class ReviewDetails {
  constructor(
    private id: number,
    private authorName: string,
    private reviewText: string,
    private rating: number,
    private likeCount: number,
    private dislikeCount: number,
    private isAnonymus: boolean,
    private createdAt: Date
  ) { }
}
