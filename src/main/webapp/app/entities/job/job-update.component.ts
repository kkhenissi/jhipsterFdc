import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJob, Job } from 'app/shared/model/job.model';
import { JobService } from './job.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';
import { IFdcUser } from 'app/shared/model/fdc-user.model';
import { FdcUserService } from 'app/entities/fdc-user/fdc-user.service';

type SelectableEntity = IItem | IFdcUser;

@Component({
  selector: 'jhi-job-update',
  templateUrl: './job-update.component.html',
})
export class JobUpdateComponent implements OnInit {
  isSaving = false;
  items: IItem[] = [];
  fdcusers: IFdcUser[] = [];

  editForm = this.fb.group({
    id: [],
    jobTitle: [],
    minSalary: [],
    maxSalary: [],
    items: [],
    fdcUser: [],
  });

  constructor(
    protected jobService: JobService,
    protected itemService: ItemService,
    protected fdcUserService: FdcUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ job }) => {
      this.updateForm(job);

      this.itemService.query().subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body || []));

      this.fdcUserService.query().subscribe((res: HttpResponse<IFdcUser[]>) => (this.fdcusers = res.body || []));
    });
  }

  updateForm(job: IJob): void {
    this.editForm.patchValue({
      id: job.id,
      jobTitle: job.jobTitle,
      minSalary: job.minSalary,
      maxSalary: job.maxSalary,
      items: job.items,
      fdcUser: job.fdcUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const job = this.createFromForm();
    if (job.id !== undefined) {
      this.subscribeToSaveResponse(this.jobService.update(job));
    } else {
      this.subscribeToSaveResponse(this.jobService.create(job));
    }
  }

  private createFromForm(): IJob {
    return {
      ...new Job(),
      id: this.editForm.get(['id'])!.value,
      jobTitle: this.editForm.get(['jobTitle'])!.value,
      minSalary: this.editForm.get(['minSalary'])!.value,
      maxSalary: this.editForm.get(['maxSalary'])!.value,
      items: this.editForm.get(['items'])!.value,
      fdcUser: this.editForm.get(['fdcUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJob>>): void {
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

  getSelected(selectedVals: IItem[], option: IItem): IItem {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
