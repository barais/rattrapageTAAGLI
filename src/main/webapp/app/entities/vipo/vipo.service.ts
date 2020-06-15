import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVipo } from 'app/shared/model/vipo.model';

type EntityResponseType = HttpResponse<IVipo>;
type EntityArrayResponseType = HttpResponse<IVipo[]>;

@Injectable({ providedIn: 'root' })
export class VipoService {
  public resourceUrl = SERVER_API_URL + 'api/vipos';

  constructor(protected http: HttpClient) {}

  create(vipo: IVipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vipo);
    return this.http
      .post<IVipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vipo: IVipo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vipo);
    return this.http
      .put<IVipo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVipo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVipo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vipo: IVipo): IVipo {
    const copy: IVipo = Object.assign({}, vipo, {
      createdDate: vipo.createdDate != null && vipo.createdDate.isValid() ? vipo.createdDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vipo: IVipo) => {
        vipo.createdDate = vipo.createdDate != null ? moment(vipo.createdDate) : null;
      });
    }
    return res;
  }
}
