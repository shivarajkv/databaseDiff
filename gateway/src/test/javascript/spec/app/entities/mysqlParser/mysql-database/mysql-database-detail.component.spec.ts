/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { MysqlDatabaseDetailComponent } from 'app/entities/mysqlParser/mysql-database/mysql-database-detail.component';
import { MysqlDatabase } from 'app/shared/model/mysqlParser/mysql-database.model';

describe('Component Tests', () => {
    describe('MysqlDatabase Management Detail Component', () => {
        let comp: MysqlDatabaseDetailComponent;
        let fixture: ComponentFixture<MysqlDatabaseDetailComponent>;
        const route = ({ data: of({ mysqlDatabase: new MysqlDatabase('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [MysqlDatabaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MysqlDatabaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MysqlDatabaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mysqlDatabase).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
