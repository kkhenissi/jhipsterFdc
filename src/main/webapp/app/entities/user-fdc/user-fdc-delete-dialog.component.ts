import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserFdc } from 'app/shared/model/user-fdc.model';
import { UserFdcService } from './user-fdc.service';

@Component({
  templateUrl: './user-fdc-delete-dialog.component.html',
})
export class UserFdcDeleteDialogComponent {
  userFdc?: IUserFdc;

  constructor(protected userFdcService: UserFdcService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userFdcService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userFdcListModification');
      this.activeModal.close();
    });
  }
}
