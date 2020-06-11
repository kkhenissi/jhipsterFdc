import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserFdc, UserFdc } from 'app/shared/model/user-fdc.model';
import { UserFdcService } from './user-fdc.service';
import { UserFdcComponent } from './user-fdc.component';
import { UserFdcDetailComponent } from './user-fdc-detail.component';
import { UserFdcUpdateComponent } from './user-fdc-update.component';

@Injectable({ providedIn: 'root' })
export class UserFdcResolve implements Resolve<IUserFdc> {
  constructor(private service: UserFdcService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserFdc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userFdc: HttpResponse<UserFdc>) => {
          if (userFdc.body) {
            return of(userFdc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserFdc());
  }
}

export const userFdcRoute: Routes = [
  {
    path: '',
    component: UserFdcComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.userFdc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserFdcDetailComponent,
    resolve: {
      userFdc: UserFdcResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.userFdc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserFdcUpdateComponent,
    resolve: {
      userFdc: UserFdcResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.userFdc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserFdcUpdateComponent,
    resolve: {
      userFdc: UserFdcResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.userFdc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
