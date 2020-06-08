import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJobHistory, JobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryService } from './job-history.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IFdcUser } from 'app/shared/model/fdc-user.model';
import { FdcUserService } from 'app/entities/fdc-user/fdc-user.service';

type SelectableEntity = IJob | IDepartment | IFdcUser;

@Component({
  selector: 'jhi-job-history-update',
  templateUrl: './job-history-update.component.html',
})
export class JobHistoryUpdateComponent implements OnInit {
  isSaving = false;
  jobs: IJob[] = [];
  departments: IDepartment[] = [];
  fdcusers: IFdcUser[] = [];

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    language: [],
    job: [],
    department: [],
    fdcUser: [],
  });

  constructor(
    protected jobHistoryService: JobHistoryService,
    protected jobService: JobService,
    protected departmentService: DepartmentService,
    protected fdcUserService: FdcUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobHistory }) => {
      if (!jobHistory.id) {
        const today = moment().startOf('day');
        jobHistory.startDate = today;
        jobHistory.endDate = today;
      }

      this.updateForm(jobHistory);

      this.jobService
        .query({ filter: 'jobhistory-is-null' })
        .pipe(
          map((res: HttpResponse<IJob[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IJob[]) => {
          if (!jobHistory.job || !jobHistory.job.id) {
            this.jobs = resBody;
          } else {
            this.jobService
              .find(jobHistory.job.id)
              .pipe(
                map((subRes: HttpResponse<IJob>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IJob[]) => (this.jobs = concatRes));
          }
        });

      this.departmentService
        .query({ filter: 'jobhistory-is-null' })
        .pipe(
          map((res: HttpResponse<IDepartment[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDepartment[]) => {
          if (!jobHistory.department || !jobHistory.department.id) {
            this.departments = resBody;
          } else {
            this.departmentService
              .find(jobHistory.department.id)
              .pipe(
                map((subRes: HttpResponse<IDepartment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDepartment[]) => (this.departments = concatRes));
          }
        });

      this.fdcUserService
        .query({ filter: 'jobhistory-is-null' })
        .pipe(
          map((res: HttpResponse<IFdcUser[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IFdcUser[]) => {
          if (!jobHistory.fdcUser || !jobHistory.fdcUser.id) {
            this.fdcusers = resBody;
          } else {
            this.fdcUserService
              .find(jobHistory.fdcUser.id)
              .pipe(
                map((subRes: HttpResponse<IFdcUser>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IFdcUser[]) => (this.fdcusers = concatRes));
          }
        });
    });
  }

  updateForm(jobHistory: IJobHistory): void {
    this.editForm.patchValue({
      id: jobHistory.id,
      startDate: jobHistory.startDate ? jobHistory.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: jobHistory.endDate ? jobHistory.endDate.format(DATE_TIME_FORMAT) : null,
      language: jobHistory.language,
      job: jobHistory.job,
      department: jobHistory.department,
      fdcUser: jobHistory.fdcUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobHistory = this.createFromForm();
    if (jobHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.jobHistoryService.update(jobHistory));
    } else {
      this.subscribeToSaveResponse(this.jobHistoryService.create(jobHistory));
    }
  }

  private createFromForm(): IJobHistory {
    return {
      ...new JobHistory(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      language: this.editForm.get(['language'])!.value,
      job: this.editForm.get(['job'])!.value,
      department: this.editForm.get(['department'])!.value,
      fdcUser: this.editForm.get(['fdcUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobHistory>>): void {
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
