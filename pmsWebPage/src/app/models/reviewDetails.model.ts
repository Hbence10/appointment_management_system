export class ReviewDetails {

  constructor(
    private _id: number,
    private _author: string,
    private _reviewText: string,
    private _rating: number,
    private _likeCount: number,
    private _dislikeCount: number,
    private _isAnonymus: boolean,
    private _createdAt: Date
  ) { }

  //Getterek:
  get id(): number {
    return this._id
  }

  get author(): string {
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

  get isAnonymus(): boolean{
    return this._isAnonymus
  }

  get createdAt(): Date {
    return this._createdAt
  }
}
