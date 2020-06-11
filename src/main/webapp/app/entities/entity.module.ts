import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.JhipsterFdcRegionModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.JhipsterFdcCountryModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JhipsterFdcLocationModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.JhipsterFdcDepartmentModule),
      },
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.JhipsterFdcItemModule),
      },
      {
        path: 'fdc-user',
        loadChildren: () => import('./fdc-user/fdc-user.module').then(m => m.JhipsterFdcFdcUserModule),
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.JhipsterFdcJobModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.JhipsterFdcJobHistoryModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.JhipsterFdcProductModule),
      },
      {
        path: 'product-item',
        loadChildren: () => import('./product-item/product-item.module').then(m => m.JhipsterFdcProductItemModule),
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.JhipsterFdcCategoryModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.JhipsterFdcPhotoModule),
      },
      {
        path: 'user-fdc',
        loadChildren: () => import('./user-fdc/user-fdc.module').then(m => m.JhipsterFdcUserFdcModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterFdcEntityModule {}
