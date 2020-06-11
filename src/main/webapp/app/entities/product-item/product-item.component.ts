import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductItem } from 'app/shared/model/product-item.model';
import { ProductItemService } from './product-item.service';
import { ProductItemDeleteDialogComponent } from './product-item-delete-dialog.component';

@Component({
  selector: 'jhi-product-item',
  templateUrl: './product-item.component.html',
})
export class ProductItemComponent implements OnInit, OnDestroy {
  productItems?: IProductItem[];
  eventSubscriber?: Subscription;

  constructor(
    protected productItemService: ProductItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productItemService.query().subscribe((res: HttpResponse<IProductItem[]>) => (this.productItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('productItemListModification', () => this.loadAll());
  }

  delete(productItem: IProductItem): void {
    const modalRef = this.modalService.open(ProductItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productItem = productItem;
  }
}
