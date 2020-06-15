import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { VipoEntry } from 'app/shared/model/vipo-entry.model';
import { VipoEntryService } from './vipo-entry.service';
import { VipoEntryComponent } from './vipo-entry.component';
import { VipoEntryDetailComponent } from './vipo-entry-detail.component';
import { VipoEntryUpdateComponent } from './vipo-entry-update.component';
import { VipoEntryDeletePopupComponent } from './vipo-entry-delete-dialog.component';
import { IVipoEntry } from 'app/shared/model/vipo-entry.model';

@Injectable({ providedIn: 'root' })
export class VipoEntryResolve implements Resolve<IVipoEntry> {
  constructor(private service: VipoEntryService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVipoEntry> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vipoEntry: HttpResponse<VipoEntry>) => vipoEntry.body));
    }
    return of(new VipoEntry());
  }
}

export const vipoEntryRoute: Routes = [
  {
    path: '',
    component: VipoEntryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'VipoEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VipoEntryDetailComponent,
    resolve: {
      vipoEntry: VipoEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VipoEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VipoEntryUpdateComponent,
    resolve: {
      vipoEntry: VipoEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VipoEntries'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VipoEntryUpdateComponent,
    resolve: {
      vipoEntry: VipoEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VipoEntries'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vipoEntryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VipoEntryDeletePopupComponent,
    resolve: {
      vipoEntry: VipoEntryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VipoEntries'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
