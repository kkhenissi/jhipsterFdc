import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserFdc } from 'app/shared/model/user-fdc.model';

@Component({
  selector: 'jhi-user-fdc-detail',
  templateUrl: './user-fdc-detail.component.html',
})
export class UserFdcDetailComponent implements OnInit {
  userFdc: IUserFdc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userFdc }) => (this.userFdc = userFdc));
  }

  previousState(): void {
    window.history.back();
  }
}
