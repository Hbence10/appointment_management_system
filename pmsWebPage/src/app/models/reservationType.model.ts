export class ReservationType{
  constructor(
    public name:string,
    public price: number,
    public amount: string,
    public isSelected: boolean = false
  ){}
}
