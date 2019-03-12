export interface IDatabase {
    id?: string;
    name?: string;
    user?: string;
    password?: string;
    address?: string;
    port?: string;
    extras?: string;
}

export class Database implements IDatabase {
    constructor(
        public id?: string,
        public name?: string,
        public user?: string,
        public password?: string,
        public address?: string,
        public port?: string,
        public extras?: string) {}
}
