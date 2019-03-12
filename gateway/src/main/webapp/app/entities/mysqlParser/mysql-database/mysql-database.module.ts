import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    MysqlDatabaseComponent,
    MysqlDatabaseDetailComponent,
    MysqlDatabaseUpdateComponent,
    MysqlDatabaseDeletePopupComponent,
    MysqlDatabaseDeleteDialogComponent,
    mysqlDatabaseRoute,
    mysqlDatabasePopupRoute
} from './';

const ENTITY_STATES = [...mysqlDatabaseRoute, ...mysqlDatabasePopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MysqlDatabaseComponent,
        MysqlDatabaseDetailComponent,
        MysqlDatabaseUpdateComponent,
        MysqlDatabaseDeleteDialogComponent,
        MysqlDatabaseDeletePopupComponent
    ],
    entryComponents: [
        MysqlDatabaseComponent,
        MysqlDatabaseUpdateComponent,
        MysqlDatabaseDeleteDialogComponent,
        MysqlDatabaseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayMysqlDatabaseModule {}
