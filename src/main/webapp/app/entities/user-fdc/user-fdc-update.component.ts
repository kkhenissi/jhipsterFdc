import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserFdc, UserFdc } from 'app/shared/model/user-fdc.model';
import { UserFdcService } from './user-fdc.service';

@Component({
  selector: 'jhi-user-fdc-update',
  templateUrl: './user-fdc-update.component.html',
})
export class UserFdcUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userNameUser: [],
    firstNameUser: [],
    lastNameUser: [],
    emailUser: [],
    passwordUser: [],
    dateRegistrationUser: [],
    avatarUser: [],
    statusUser: [],
  });

  constructor(protected userFdcService: UserFdcService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userFdc }) => {
      if (!userFdc.id) {
        const today = moment().startOf('day');
        userFdc.dateRegistrationUser = today;
      }

      this.updateForm(userFdc);
    });
  }

  updateForm(userFdc: IUserFdc): void {
    this.editForm.patchValue({
      id: userFdc.id,
      userNameUser: userFdc.userNameUser,
      firstNameUser: userFdc.firstNameUser,
      lastNameUser: userFdc.lastNameUser,
      emailUser: userFdc.emailUser,
      passwordUser: userFdc.passwordUser,
      dateRegistrationUser: userFdc.dateRegistrationUser ? userFdc.dateRegistrationUser.format(DATE_TIME_FORMAT) : null,
      avatarUser: userFdc.avatarUser,
      statusUser: userFdc.statusUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userFdc = this.createFromForm();
    if (userFdc.id !== undefined) {
      this.subscribeToSaveResponse(this.userFdcService.update(userFdc));
    } else {
      this.subscribeToSaveResponse(this.userFdcService.create(userFdc));
    }
  }

  private createFromForm(): IUserFdc {
    return {
      ...new UserFdc(),
      id: this.editForm.get(['id'])!.value,
      userNameUser: this.editForm.get(['userNameUser'])!.value,
      firstNameUser: this.editForm.get(['firstNameUser'])!.value,
      lastNameUser: this.editForm.get(['lastNameUser'])!.value,
      emailUser: this.editForm.get(['emailUser'])!.value,
      passwordUser: this.editForm.get(['passwordUser'])!.value,
      dateRegistrationUser: this.editForm.get(['dateRegistrationUser'])!.value
        ? moment(this.editForm.get(['dateRegistrationUser'])!.value, DATE_TIME_FORMAT)
        : undefined,
      avatarUser: this.editForm.get(['avatarUser'])!.value,
      statusUser: this.editForm.get(['statusUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserFdc>>): void {
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
}
