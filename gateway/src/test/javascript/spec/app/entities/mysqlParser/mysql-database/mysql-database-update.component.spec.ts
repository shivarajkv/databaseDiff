/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { MysqlDatabaseUpdateComponent } from 'app/entities/mysqlParser/mysql-database/mysql-database-update.component';
import { MysqlDatabaseService } from 'app/entities/mysqlParser/mysql-database/mysql-database.service';
import { MysqlDatabase } from 'app/shared/model/mysqlParser/mysql-database.model';

describe('Component Tests', () => {
    describe('MysqlDatabase Management Update Component', () => {
        let comp: MysqlDatabaseUpdateComponent;
        let fixture: ComponentFixture<MysqlDatabaseUpdateComponent>;
        let service: MysqlDatabaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [MysqlDatabaseUpdateComponent]
            })
                .overrideTemplate(MysqlDatabaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MysqlDatabaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MysqlDatabaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MysqlDatabase('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mysqlDatabase = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MysqlDatabase();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mysqlDatabase = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
