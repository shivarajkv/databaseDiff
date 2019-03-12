export interface IMysqlDatabase {
    id?: string;
    version?: number;
    tables?: string;
}

export class MysqlDatabase implements IMysqlDatabase {
    constructor(public id?: string, public version?: number, public tables?: string) {}
}
