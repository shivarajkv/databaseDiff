import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { MatSnackBar } from '@angular/material';

@Component({
    selector: 'jhi-alert',
    template: ''
    // template: `
    //     <div class="alerts" role="alert">
    //         <div *ngFor="let alert of alerts" [ngClass]="{\'alert.position\': true, \'toast\': alert.toast}">
    //             <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
    //                 <pre [innerHTML]="alert.msg"></pre>
    //             </ngb-alert>
    //         </div>
    //     </div>`
})
export class JhiAlertComponent implements OnInit, OnDestroy {
    alerts: any[];

    constructor(private alertService: JhiAlertService, private snackbar: MatSnackBar) { }

    ngOnInit() {
        this.alerts = this.alertService.get();
        this.snackbar.open(this.alerts['msg']);
    }

    ngOnDestroy() {
        this.alerts = [];
    }
}
