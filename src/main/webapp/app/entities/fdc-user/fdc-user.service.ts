import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFdcUser } from 'app/shared/model/fdc-user.model';

type EntityResponseType = HttpResponse<IFdcUser>;
type EntityArrayResponseType = HttpResponse<IFdcUser[]>;

@Injectable({ providedIn: 'root' })
export class FdcUserService {
  public resourceUrl = SERVER_API_URL + 'api/fdc-users';

  constructor(protected http: HttpClient) {}

  create(fdcUser: IFdcUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fdcUser);
    return this.http
      .post<IFdcUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fdcUser: IFdcUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fdcUser);
    return this.http
      .put<IFdcUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFdcUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFdcUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fdcUser: IFdcUser): IFdcUser {
    const copy: IFdcUser = Object.assign({}, fdcUser, {
      hireDate: fdcUser.hireDate && fdcUser.hireDate.isValid() ? fdcUser.hireDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hireDate = res.body.hireDate ? moment(res.body.hireDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fdcUser: IFdcUser) => {
        fdcUser.hireDate = fdcUser.hireDate ? moment(fdcUser.hireDate) : undefined;
      });
    }
    return res;
  }
}
