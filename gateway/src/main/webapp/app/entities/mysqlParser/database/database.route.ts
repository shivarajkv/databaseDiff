import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IDatabase, Database } from 'app/shared/model/mysqlParser/database.model';
import { DatabaseService } from 'app/entities/mysqlParser/database/database.service';
import { DatabaseUpdateComponent } from 'app/entities/mysqlParser/database/database-update.component';

@Injectable({ providedIn: 'root' })
export class DatabaseResolve implements Resolve<IDatabase> {
    constructor(private service: DatabaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((database: HttpResponse<Database>) => database.body));
        }
        return of(new Database());
    }
}

export const databaseRoute: Routes = [
    {
        path: 'database/new',
        component: DatabaseUpdateComponent,
        resolve: {
            database: DatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.mysqlParserMysqlDatabase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
