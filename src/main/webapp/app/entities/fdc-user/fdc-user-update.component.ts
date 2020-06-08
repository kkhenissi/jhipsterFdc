import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFdcUser, FdcUser } from 'app/shared/model/fdc-user.model';
import { FdcUserService } from './fdc-user.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

type SelectableEntity = IFdcUser | IDepartment;

@Component({
  selector: 'jhi-fdc-user-update',
  templateUrl: './fdc-user-update.component.html',
})
export class FdcUserUpdateComponent implements OnInit {
  isSaving = false;
  fdcusers: IFdcUser[] = [];
  departments: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    hireDate: [],
    manager: [],
    department: [],
  });

  constructor(
    protected fdcUserService: FdcUserService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fdcUser }) => {
      if (!fdcUser.id) {
        const today = moment().startOf('day');
        fdcUser.hireDate = today;
      }

      this.updateForm(fdcUser);

      this.fdcUserService.query().subscribe((res: HttpResponse<IFdcUser[]>) => (this.fdcusers = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  updateForm(fdcUser: IFdcUser): void {
    this.editForm.patchValue({
      id: fdcUser.id,
      firstName: fdcUser.firstName,
      lastName: fdcUser.lastName,
      email: fdcUser.email,
      phoneNumber: fdcUser.phoneNumber,
      hireDate: fdcUser.hireDate ? fdcUser.hireDate.format(DATE_TIME_FORMAT) : null,
      manager: fdcUser.manager,
      department: fdcUser.department,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fdcUser = this.createFromForm();
    if (fdcUser.id !== undefined) {
      this.subscribeToSaveResponse(this.fdcUserService.update(fdcUser));
    } else {
      this.subscribeToSaveResponse(this.fdcUserService.create(fdcUser));
    }
  }

  private createFromForm(): IFdcUser {
    return {
      ...new FdcUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      hireDate: this.editForm.get(['hireDate'])!.value ? moment(this.editForm.get(['hireDate'])!.value, DATE_TIME_FORMAT) : undefined,
      manager: this.editForm.get(['manager'])!.value,
      department: this.editForm.get(['department'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFdcUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
