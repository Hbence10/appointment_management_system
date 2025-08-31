export class NewsDetails {
  constructor(
    public id: number,
    public title: string,
    public text: string,
    public bannerImgPath: string,
    public placement: number,
    public createdAt: Date,
    public isExpand: boolean = this.placement == 1 ? true : false
  ) { }
}
