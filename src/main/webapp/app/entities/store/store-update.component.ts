import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IStore, Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IVipo } from 'app/shared/model/vipo.model';
import { VipoService } from 'app/entities/vipo/vipo.service';

@Component({
  selector: 'jhi-store-update',
  templateUrl: './store-update.component.html'
})
export class StoreUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  vipos: IVipo[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [null, [Validators.required]],
    postalCode: [null, [Validators.required]],
    city: [null, [Validators.required]],
    country: [null, [Validators.required]],
    userId: [],
    vipoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected storeService: StoreService,
    protected userService: UserService,
    protected vipoService: VipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ store }) => {
      this.updateForm(store);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.vipoService.query({ filter: 'store-is-null' }).subscribe(
      (res: HttpResponse<IVipo[]>) => {
        if (!this.editForm.get('vipoId').value) {
          this.vipos = res.body;
        } else {
          this.vipoService
            .find(this.editForm.get('vipoId').value)
            .subscribe(
              (subRes: HttpResponse<IVipo>) => (this.vipos = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(store: IStore) {
    this.editForm.patchValue({
      id: store.id,
      name: store.name,
      address: store.address,
      postalCode: store.postalCode,
      city: store.city,
      country: store.country,
      userId: store.userId,
      vipoId: store.vipoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const store = this.createFromForm();
    if (store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(store));
    }
  }

  private createFromForm(): IStore {
    return {
      ...new Store(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      city: this.editForm.get(['city']).value,
      country: this.editForm.get(['country']).value,
      userId: this.editForm.get(['userId']).value,
      vipoId: this.editForm.get(['vipoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>) {
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

  trackVipoById(index: number, item: IVipo) {
    return item.id;
  }
}
