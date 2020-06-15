import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVipoEntry, VipoEntry } from 'app/shared/model/vipo-entry.model';
import { VipoEntryService } from './vipo-entry.service';
import { IVipo } from 'app/shared/model/vipo.model';
import { VipoService } from 'app/entities/vipo/vipo.service';

@Component({
  selector: 'jhi-vipo-entry-update',
  templateUrl: './vipo-entry-update.component.html'
})
export class VipoEntryUpdateComponent implements OnInit {
  isSaving: boolean;

  vipos: IVipo[];
  registerDateDp: any;

  editForm = this.fb.group({
    id: [],
    registerDate: [null, [Validators.required]],
    imageName: [null, [Validators.required]],
    vipoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vipoEntryService: VipoEntryService,
    protected vipoService: VipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vipoEntry }) => {
      this.updateForm(vipoEntry);
    });
    this.vipoService
      .query()
      .subscribe((res: HttpResponse<IVipo[]>) => (this.vipos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vipoEntry: IVipoEntry) {
    this.editForm.patchValue({
      id: vipoEntry.id,
      registerDate: vipoEntry.registerDate,
      imageName: vipoEntry.imageName,
      vipoId: vipoEntry.vipoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vipoEntry = this.createFromForm();
    if (vipoEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.vipoEntryService.update(vipoEntry));
    } else {
      this.subscribeToSaveResponse(this.vipoEntryService.create(vipoEntry));
    }
  }

  private createFromForm(): IVipoEntry {
    return {
      ...new VipoEntry(),
      id: this.editForm.get(['id']).value,
      registerDate: this.editForm.get(['registerDate']).value,
      imageName: this.editForm.get(['imageName']).value,
      vipoId: this.editForm.get(['vipoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVipoEntry>>) {
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

  trackVipoById(index: number, item: IVipo) {
    return item.id;
  }
}
