import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBrand, Brand } from 'app/shared/model/brand.model';
import { BrandService } from './brand.service';
import { BrandComponent } from './brand.component';
import { BrandDetailComponent } from './brand-detail.component';
import { BrandUpdateComponent } from './brand-update.component';

@Injectable({ providedIn: 'root' })
export class BrandResolve implements Resolve<IBrand> {
  constructor(private service: BrandService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBrand> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((brand: HttpResponse<Brand>) => {
          if (brand.body) {
            return of(brand.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Brand());
  }
}

export const brandRoute: Routes = [
  {
    path: '',
    component: BrandComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.brand.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BrandDetailComponent,
    resolve: {
      brand: BrandResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.brand.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BrandUpdateComponent,
    resolve: {
      brand: BrandResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.brand.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BrandUpdateComponent,
    resolve: {
      brand: BrandResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterFdcApp.brand.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
