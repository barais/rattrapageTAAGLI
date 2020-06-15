import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImageProperty } from 'app/shared/model/image-property.model';

type EntityResponseType = HttpResponse<IImageProperty>;
type EntityArrayResponseType = HttpResponse<IImageProperty[]>;

@Injectable({ providedIn: 'root' })
export class ImagePropertyService {
  public resourceUrl = SERVER_API_URL + 'api/image-properties';

  constructor(protected http: HttpClient) {}

  create(imageProperty: IImageProperty): Observable<EntityResponseType> {
    return this.http.post<IImageProperty>(this.resourceUrl, imageProperty, { observe: 'response' });
  }

  update(imageProperty: IImageProperty): Observable<EntityResponseType> {
    return this.http.put<IImageProperty>(this.resourceUrl, imageProperty, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImageProperty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImageProperty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
