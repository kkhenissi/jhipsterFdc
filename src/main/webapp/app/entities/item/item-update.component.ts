import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IItem, Item } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { IPhoto } from 'app/shared/model/photo.model';
import { PhotoService } from 'app/entities/photo/photo.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

type SelectableEntity = IPhoto | ICategory;

@Component({
  selector: 'jhi-item-update',
  templateUrl: './item-update.component.html',
})
export class ItemUpdateComponent implements OnInit {
  isSaving = false;
  photos: IPhoto[] = [];
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    currentPrice: [],
    statusItem: [],
    photos: [],
    category: [],
  });

  constructor(
    protected itemService: ItemService,
    protected photoService: PhotoService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ item }) => {
      this.updateForm(item);

      this.photoService
        .query({ filter: 'item-is-null' })
        .pipe(
          map((res: HttpResponse<IPhoto[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPhoto[]) => {
          if (!item.photos || !item.photos.id) {
            this.photos = resBody;
          } else {
            this.photoService
              .find(item.photos.id)
              .pipe(
                map((subRes: HttpResponse<IPhoto>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPhoto[]) => (this.photos = concatRes));
          }
        });

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      title: item.title,
      description: item.description,
      currentPrice: item.currentPrice,
      statusItem: item.statusItem,
      photos: item.photos,
      category: item.category,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  private createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      currentPrice: this.editForm.get(['currentPrice'])!.value,
      statusItem: this.editForm.get(['statusItem'])!.value,
      photos: this.editForm.get(['photos'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
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
