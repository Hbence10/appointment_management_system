export class ReservedHours{
  constructor(
    private _id: number,
    private _start: number,
    private _end: number
  ){}

  //Getterek:
  get id(): number {
    return this._id
  }

  get start(){
    return this._start
  }

  get end(){
    return this._end
  }
}
