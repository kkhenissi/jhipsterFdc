import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhoto } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { PhotoDeleteDialogComponent } from './photo-delete-dialog.component';

@Component({
  selector: 'jhi-photo',
  templateUrl: './photo.component.html',
})
export class PhotoComponent implements OnInit, OnDestroy {
  photos?: IPhoto[];
  eventSubscriber?: Subscription;

  constructor(protected photoService: PhotoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.photoService.query().subscribe((res: HttpResponse<IPhoto[]>) => (this.photos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhotos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhoto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPhotos(): void {
    this.eventSubscriber = this.eventManager.subscribe('photoListModification', () => this.loadAll());
  }

  delete(photo: IPhoto): void {
    const modalRef = this.modalService.open(PhotoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.photo = photo;
  }
}
