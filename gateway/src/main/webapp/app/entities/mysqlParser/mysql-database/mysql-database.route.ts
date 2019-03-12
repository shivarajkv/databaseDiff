import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MysqlDatabase } from 'app/shared/model/mysqlParser/mysql-database.model';
import { MysqlDatabaseService } from './mysql-database.service';
import { MysqlDatabaseComponent } from './mysql-database.component';
import { MysqlDatabaseDetailComponent } from './mysql-database-detail.component';
import { MysqlDatabaseUpdateComponent } from './mysql-database-update.component';
import { MysqlDatabaseDeletePopupComponent } from './mysql-database-delete-dialog.component';
import { IMysqlDatabase } from 'app/shared/model/mysqlParser/mysql-database.model';

@Injectable({ providedIn: 'root' })
export class MysqlDatabaseResolve implements Resolve<IMysqlDatabase> {
    constructor(private service: MysqlDatabaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((mysqlDatabase: HttpResponse<MysqlDatabase>) => mysqlDatabase.body));
        }
        return of(new MysqlDatabase());
    }
}

export const mysqlDatabaseRoute: Routes = [
    {
        path: 'mysql-database',
        component: MysqlDatabaseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'gatewayApp.mysqlParserMysqlDatabase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mysql-database/:id/view',
        component: MysqlDatabaseDetailComponent,
        resolve: {
            mysqlDatabase: MysqlDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.mysqlParserMysqlDatabase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mysql-database/new',
        component: MysqlDatabaseUpdateComponent,
        resolve: {
            mysqlDatabase: MysqlDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.mysqlParserMysqlDatabase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mysql-database/:id/edit',
        component: MysqlDatabaseUpdateComponent,
        resolve: {
            mysqlDatabase: MysqlDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.mysqlParserMysqlDatabase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mysqlDatabasePopupRoute: Routes = [
    {
        path: 'mysql-database/:id/delete',
        component: MysqlDatabaseDeletePopupComponent,
        resolve: {
            mysqlDatabase: MysqlDatabaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.mysqlParserMysqlDatabase.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
