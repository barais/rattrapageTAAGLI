import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ImageProperty } from 'app/shared/model/image-property.model';
import { ImagePropertyService } from './image-property.service';
import { ImagePropertyComponent } from './image-property.component';
import { ImagePropertyDetailComponent } from './image-property-detail.component';
import { ImagePropertyUpdateComponent } from './image-property-update.component';
import { ImagePropertyDeletePopupComponent } from './image-property-delete-dialog.component';
import { IImageProperty } from 'app/shared/model/image-property.model';

@Injectable({ providedIn: 'root' })
export class ImagePropertyResolve implements Resolve<IImageProperty> {
  constructor(private service: ImagePropertyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImageProperty> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((imageProperty: HttpResponse<ImageProperty>) => imageProperty.body));
    }
    return of(new ImageProperty());
  }
}

export const imagePropertyRoute: Routes = [
  {
    path: '',
    component: ImagePropertyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ImageProperties'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImagePropertyDetailComponent,
    resolve: {
      imageProperty: ImagePropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ImageProperties'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImagePropertyUpdateComponent,
    resolve: {
      imageProperty: ImagePropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ImageProperties'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImagePropertyUpdateComponent,
    resolve: {
      imageProperty: ImagePropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ImageProperties'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const imagePropertyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImagePropertyDeletePopupComponent,
    resolve: {
      imageProperty: ImagePropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ImageProperties'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
