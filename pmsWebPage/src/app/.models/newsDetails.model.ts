export class NewsDetails {
  constructor(
    private id: number,
    private title: string,
    private text: string,
    private bannerImgPath: string,
    private placement: number,
    private createdAt: Date
  ) { }
}
