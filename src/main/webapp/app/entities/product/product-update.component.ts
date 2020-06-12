import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { SubCategoryService } from 'app/entities/sub-category/sub-category.service';
import { IBrand } from 'app/shared/model/brand.model';
import { BrandService } from 'app/entities/brand/brand.service';

type SelectableEntity = ISubCategory | IBrand;

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  subcategories: ISubCategory[] = [];
  brands: IBrand[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    image: [],
    imageContentType: [],
    price: [],
    size: [],
    availableUntil: [],
    subcategories: [],
    brand: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productService: ProductService,
    protected subCategoryService: SubCategoryService,
    protected brandService: BrandService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      if (!product.id) {
        const today = moment().startOf('day');
        product.availableUntil = today;
      }

      this.updateForm(product);

      this.subCategoryService.query().subscribe((res: HttpResponse<ISubCategory[]>) => (this.subcategories = res.body || []));

      this.brandService.query().subscribe((res: HttpResponse<IBrand[]>) => (this.brands = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      description: product.description,
      image: product.image,
      imageContentType: product.imageContentType,
      price: product.price,
      size: product.size,
      availableUntil: product.availableUntil ? product.availableUntil.format(DATE_TIME_FORMAT) : null,
      subcategories: product.subcategories,
      brand: product.brand,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhipsterFdcApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      price: this.editForm.get(['price'])!.value,
      size: this.editForm.get(['size'])!.value,
      availableUntil: this.editForm.get(['availableUntil'])!.value
        ? moment(this.editForm.get(['availableUntil'])!.value, DATE_TIME_FORMAT)
        : undefined,
      subcategories: this.editForm.get(['subcategories'])!.value,
      brand: this.editForm.get(['brand'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  getSelected(selectedVals: ISubCategory[], option: ISubCategory): ISubCategory {
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
