import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMysqlDatabase } from 'app/shared/model/mysqlParser/mysql-database.model';

@Component({
    selector: 'jhi-mysql-database-detail',
    templateUrl: './mysql-database-detail.component.html'
})
export class MysqlDatabaseDetailComponent implements OnInit {
    mysqlDatabase: IMysqlDatabase;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mysqlDatabase }) => {
            this.mysqlDatabase = mysqlDatabase;
        });
    }

    previousState() {
        window.history.back();
    }
}
