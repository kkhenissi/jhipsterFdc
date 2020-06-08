import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFdcUser, FdcUser } from 'app/shared/model/fdc-user.model';
import { FdcUserService } from './fdc-user.service';
import { FdcUserComponent } from './fdc-user.component';
import { FdcUserDetailComponent } from './fdc-user-detail.component';
import { FdcUserUpdateComponent } from './fdc-user-update.component';

@Injectable({ providedIn: 'root' })
export class FdcUserResolve implements Resolve<IFdcUser> {
  constructor(private service: FdcUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFdcUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fdcUser: HttpResponse<FdcUser>) => {
          if (fdcUser.body) {
            return of(fdcUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FdcUser());
  }
}

export const fdcUserRoute: Routes = [
  {
    path: '',
    component: FdcUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.fdcUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FdcUserDetailComponent,
    resolve: {
      fdcUser: FdcUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.fdcUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FdcUserUpdateComponent,
    resolve: {
      fdcUser: FdcUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.fdcUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FdcUserUpdateComponent,
    resolve: {
      fdcUser: FdcUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.fdcUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
