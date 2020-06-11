import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserFdc } from 'app/shared/model/user-fdc.model';
import { UserFdcService } from './user-fdc.service';
import { UserFdcDeleteDialogComponent } from './user-fdc-delete-dialog.component';

@Component({
  selector: 'jhi-user-fdc',
  templateUrl: './user-fdc.component.html',
})
export class UserFdcComponent implements OnInit, OnDestroy {
  userFdcs?: IUserFdc[];
  eventSubscriber?: Subscription;

  constructor(protected userFdcService: UserFdcService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.userFdcService.query().subscribe((res: HttpResponse<IUserFdc[]>) => (this.userFdcs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserFdcs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserFdc): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserFdcs(): void {
    this.eventSubscriber = this.eventManager.subscribe('userFdcListModification', () => this.loadAll());
  }

  delete(userFdc: IUserFdc): void {
    const modalRef = this.modalService.open(UserFdcDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userFdc = userFdc;
  }
}
