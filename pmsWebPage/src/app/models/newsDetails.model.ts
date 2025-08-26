export class NewsDetails {
  constructor(
    public id: number,
    public title: string,
    public text: string,
    public bannerImgPath: string,
    public placement: number,
    public createdAt: Date
  ) { }
}
