/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { MysqlDatabaseDeleteDialogComponent } from 'app/entities/mysqlParser/mysql-database/mysql-database-delete-dialog.component';
import { MysqlDatabaseService } from 'app/entities/mysqlParser/mysql-database/mysql-database.service';

describe('Component Tests', () => {
    describe('MysqlDatabase Management Delete Component', () => {
        let comp: MysqlDatabaseDeleteDialogComponent;
        let fixture: ComponentFixture<MysqlDatabaseDeleteDialogComponent>;
        let service: MysqlDatabaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [MysqlDatabaseDeleteDialogComponent]
            })
                .overrideTemplate(MysqlDatabaseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MysqlDatabaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MysqlDatabaseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
