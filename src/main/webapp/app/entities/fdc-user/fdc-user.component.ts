import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFdcUser } from 'app/shared/model/fdc-user.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FdcUserService } from './fdc-user.service';
import { FdcUserDeleteDialogComponent } from './fdc-user-delete-dialog.component';

@Component({
  selector: 'jhi-fdc-user',
  templateUrl: './fdc-user.component.html',
})
export class FdcUserComponent implements OnInit, OnDestroy {
  fdcUsers: IFdcUser[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected fdcUserService: FdcUserService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.fdcUsers = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.fdcUserService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IFdcUser[]>) => this.paginateFdcUsers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.fdcUsers = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFdcUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFdcUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFdcUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('fdcUserListModification', () => this.reset());
  }

  delete(fdcUser: IFdcUser): void {
    const modalRef = this.modalService.open(FdcUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fdcUser = fdcUser;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFdcUsers(data: IFdcUser[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.fdcUsers.push(data[i]);
      }
    }
  }
}
