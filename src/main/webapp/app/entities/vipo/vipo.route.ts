import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vipo } from 'app/shared/model/vipo.model';
import { VipoService } from './vipo.service';
import { VipoComponent } from './vipo.component';
import { VipoDetailComponent } from './vipo-detail.component';
import { VipoUpdateComponent } from './vipo-update.component';
import { VipoDeletePopupComponent } from './vipo-delete-dialog.component';
import { IVipo } from 'app/shared/model/vipo.model';

@Injectable({ providedIn: 'root' })
export class VipoResolve implements Resolve<IVipo> {
  constructor(private service: VipoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVipo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vipo: HttpResponse<Vipo>) => vipo.body));
    }
    return of(new Vipo());
  }
}

export const vipoRoute: Routes = [
  {
    path: '',
    component: VipoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Vipos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VipoDetailComponent,
    resolve: {
      vipo: VipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vipos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VipoUpdateComponent,
    resolve: {
      vipo: VipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vipos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VipoUpdateComponent,
    resolve: {
      vipo: VipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vipos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vipoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VipoDeletePopupComponent,
    resolve: {
      vipo: VipoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vipos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
