import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVipoEntry } from 'app/shared/model/vipo-entry.model';

type EntityResponseType = HttpResponse<IVipoEntry>;
type EntityArrayResponseType = HttpResponse<IVipoEntry[]>;

@Injectable({ providedIn: 'root' })
export class VipoEntryService {
  public resourceUrl = SERVER_API_URL + 'api/vipo-entries';

  constructor(protected http: HttpClient) {}

  create(vipoEntry: IVipoEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vipoEntry);
    return this.http
      .post<IVipoEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vipoEntry: IVipoEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vipoEntry);
    return this.http
      .put<IVipoEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVipoEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVipoEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vipoEntry: IVipoEntry): IVipoEntry {
    const copy: IVipoEntry = Object.assign({}, vipoEntry, {
      registerDate: vipoEntry.registerDate != null && vipoEntry.registerDate.isValid() ? vipoEntry.registerDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registerDate = res.body.registerDate != null ? moment(res.body.registerDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vipoEntry: IVipoEntry) => {
        vipoEntry.registerDate = vipoEntry.registerDate != null ? moment(vipoEntry.registerDate) : null;
      });
    }
    return res;
  }
}
