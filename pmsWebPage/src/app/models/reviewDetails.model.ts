export class ReviewDetails {
  constructor(
    public id: number,
    public author: string,
    public reviewText: string,
    public rating: number,
    public likeCount: number,
    public dislikeCount: number,
    public isAnonymus: boolean,
    public createdAt: Date
  ) { }
}
