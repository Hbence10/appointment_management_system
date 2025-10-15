export class History {
  constructor(
    private id: number,
    private tableName: string,
    private columnName: string,
    private rowId: number,
    private oldValue: string,
    private newValue: string,
    private editedAt: Date,
    private isActive: boolean
  ) { }

  // Getterek
  get getId(): number {
    return this.id
  }

  get getTableName(): string {
    return this.tableName;
  }

  get getColumnName(): string {
    return this.columnName;
  }

  get getRowId(): number {
    return this.rowId
  }

  get getOldValue(): string {
    return this.oldValue
  }

  get getNewValue(): string {
    return this.newValue;
  }

  get getEditedAt(): Date {
    return this.editedAt;
  }

  get getIsActive(): boolean {
    return this.isActive
  }
}
