import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFdcUser } from 'app/shared/model/fdc-user.model';

@Component({
  selector: 'jhi-fdc-user-detail',
  templateUrl: './fdc-user-detail.component.html',
})
export class FdcUserDetailComponent implements OnInit {
  fdcUser: IFdcUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fdcUser }) => (this.fdcUser = fdcUser));
  }

  previousState(): void {
    window.history.back();
  }
}
