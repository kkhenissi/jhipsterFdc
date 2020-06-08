import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFdcUser } from 'app/shared/model/fdc-user.model';
import { FdcUserService } from './fdc-user.service';

@Component({
  templateUrl: './fdc-user-delete-dialog.component.html',
})
export class FdcUserDeleteDialogComponent {
  fdcUser?: IFdcUser;

  constructor(protected fdcUserService: FdcUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fdcUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fdcUserListModification');
      this.activeModal.close();
    });
  }
}
