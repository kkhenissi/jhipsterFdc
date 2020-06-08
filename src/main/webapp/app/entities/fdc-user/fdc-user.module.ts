import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterFdcSharedModule } from 'app/shared/shared.module';
import { FdcUserComponent } from './fdc-user.component';
import { FdcUserDetailComponent } from './fdc-user-detail.component';
import { FdcUserUpdateComponent } from './fdc-user-update.component';
import { FdcUserDeleteDialogComponent } from './fdc-user-delete-dialog.component';
import { fdcUserRoute } from './fdc-user.route';

@NgModule({
  imports: [JhipsterFdcSharedModule, RouterModule.forChild(fdcUserRoute)],
  declarations: [FdcUserComponent, FdcUserDetailComponent, FdcUserUpdateComponent, FdcUserDeleteDialogComponent],
  entryComponents: [FdcUserDeleteDialogComponent],
})
export class JhipsterFdcFdcUserModule {}
