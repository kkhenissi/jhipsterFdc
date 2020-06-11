import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserFdc } from 'app/shared/model/user-fdc.model';

type EntityResponseType = HttpResponse<IUserFdc>;
type EntityArrayResponseType = HttpResponse<IUserFdc[]>;

@Injectable({ providedIn: 'root' })
export class UserFdcService {
  public resourceUrl = SERVER_API_URL + 'api/user-fdcs';

  constructor(protected http: HttpClient) {}

  create(userFdc: IUserFdc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userFdc);
    return this.http
      .post<IUserFdc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userFdc: IUserFdc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userFdc);
    return this.http
      .put<IUserFdc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserFdc>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserFdc[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userFdc: IUserFdc): IUserFdc {
    const copy: IUserFdc = Object.assign({}, userFdc, {
      dateRegistrationUser:
        userFdc.dateRegistrationUser && userFdc.dateRegistrationUser.isValid() ? userFdc.dateRegistrationUser.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateRegistrationUser = res.body.dateRegistrationUser ? moment(res.body.dateRegistrationUser) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userFdc: IUserFdc) => {
        userFdc.dateRegistrationUser = userFdc.dateRegistrationUser ? moment(userFdc.dateRegistrationUser) : undefined;
      });
    }
    return res;
  }
}
