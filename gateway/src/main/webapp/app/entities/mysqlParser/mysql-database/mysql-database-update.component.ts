import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMysqlDatabase } from 'app/shared/model/mysqlParser/mysql-database.model';
import { MysqlDatabaseService } from './mysql-database.service';

@Component({
    selector: 'jhi-mysql-database-update',
    templateUrl: './mysql-database-update.component.html'
})
export class MysqlDatabaseUpdateComponent implements OnInit {
    private _mysqlDatabase: IMysqlDatabase;
    isSaving: boolean;

    constructor(private mysqlDatabaseService: MysqlDatabaseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mysqlDatabase }) => {
            this.mysqlDatabase = mysqlDatabase;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mysqlDatabase.id !== undefined) {
            this.subscribeToSaveResponse(this.mysqlDatabaseService.update(this.mysqlDatabase));
        } else {
            this.subscribeToSaveResponse(this.mysqlDatabaseService.create(this.mysqlDatabase));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMysqlDatabase>>) {
        result.subscribe((res: HttpResponse<IMysqlDatabase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get mysqlDatabase() {
        return this._mysqlDatabase;
    }

    set mysqlDatabase(mysqlDatabase: IMysqlDatabase) {
        this._mysqlDatabase = mysqlDatabase;
    }
}
