import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterFdcSharedModule } from 'app/shared/shared.module';
import { UserFdcComponent } from './user-fdc.component';
import { UserFdcDetailComponent } from './user-fdc-detail.component';
import { UserFdcUpdateComponent } from './user-fdc-update.component';
import { UserFdcDeleteDialogComponent } from './user-fdc-delete-dialog.component';
import { userFdcRoute } from './user-fdc.route';

@NgModule({
  imports: [JhipsterFdcSharedModule, RouterModule.forChild(userFdcRoute)],
  declarations: [UserFdcComponent, UserFdcDetailComponent, UserFdcUpdateComponent, UserFdcDeleteDialogComponent],
  entryComponents: [UserFdcDeleteDialogComponent],
})
export class JhipsterFdcUserFdcModule {}
