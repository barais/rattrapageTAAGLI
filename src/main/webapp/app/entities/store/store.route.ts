import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';
import { StoreComponent } from './store.component';
import { StoreDetailComponent } from './store-detail.component';
import { StoreUpdateComponent } from './store-update.component';
import { StoreDeletePopupComponent } from './store-delete-dialog.component';
import { IStore } from 'app/shared/model/store.model';

@Injectable({ providedIn: 'root' })
export class StoreResolve implements Resolve<IStore> {
  constructor(private service: StoreService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStore> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((store: HttpResponse<Store>) => store.body));
    }
    return of(new Store());
  }
}

export const storeRoute: Routes = [
  {
    path: '',
    component: StoreComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StoreDetailComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StoreUpdateComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StoreUpdateComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const storePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StoreDeletePopupComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
