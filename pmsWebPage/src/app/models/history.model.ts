export class History{
  constructor(
    private id:number,
    private tableName: string,
    private columnName: string,
    private rowId: number,
    private oldValue: string,
    private newValue: string,
    private editedAt: Date,
    private isActive: boolean
  ){}

  //

  //
}
