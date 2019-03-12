import { Component, OnInit } from '@angular/core';
import { IDatabase } from 'app/shared/model/mysqlParser/database.model';
import { DatabaseService } from 'app/entities/mysqlParser/database/database.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-database',
    templateUrl: './database-update.component.html'
})
export class DatabaseUpdateComponent implements OnInit {

    database: IDatabase;

    constructor(private service: DatabaseService, private activatedRoute: ActivatedRoute) { }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ database }) => {
            this.database = database;
        });
     }

     save(): void {
        if (this.database.id !== undefined) {
            this.subscribeToSaveResponse(this.service.update(this.database));
        } else {
            this.subscribeToSaveResponse(this.service.create(this.database));
        }
     }

     private subscribeToSaveResponse(result: Observable<HttpResponse<IDatabase>>) {
        result.subscribe((res: HttpResponse<IDatabase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.previousState();
    }

    private onSaveError() {
    }

    previousState() {
        window.history.back();
    }
}
