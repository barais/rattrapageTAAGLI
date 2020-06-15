import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVipo, Vipo } from 'app/shared/model/vipo.model';
import { VipoService } from './vipo.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store/store.service';

@Component({
  selector: 'jhi-vipo-update',
  templateUrl: './vipo-update.component.html'
})
export class VipoUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  stores: IStore[];
  createdDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    longitude: [],
    lattitude: [],
    createdDate: [null, [Validators.required]],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vipoService: VipoService,
    protected userService: UserService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vipo }) => {
      this.updateForm(vipo);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.storeService
      .query()
      .subscribe((res: HttpResponse<IStore[]>) => (this.stores = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vipo: IVipo) {
    this.editForm.patchValue({
      id: vipo.id,
      name: vipo.name,
      longitude: vipo.longitude,
      lattitude: vipo.lattitude,
      createdDate: vipo.createdDate,
      userId: vipo.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vipo = this.createFromForm();
    if (vipo.id !== undefined) {
      this.subscribeToSaveResponse(this.vipoService.update(vipo));
    } else {
      this.subscribeToSaveResponse(this.vipoService.create(vipo));
    }
  }

  private createFromForm(): IVipo {
    return {
      ...new Vipo(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      longitude: this.editForm.get(['longitude']).value,
      lattitude: this.editForm.get(['lattitude']).value,
      createdDate: this.editForm.get(['createdDate']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVipo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackStoreById(index: number, item: IStore) {
    return item.id;
  }
}
