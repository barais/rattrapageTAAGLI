import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IImageProperty, ImageProperty } from 'app/shared/model/image-property.model';
import { ImagePropertyService } from './image-property.service';
import { IVipoEntry } from 'app/shared/model/vipo-entry.model';
import { VipoEntryService } from 'app/entities/vipo-entry/vipo-entry.service';

@Component({
  selector: 'jhi-image-property-update',
  templateUrl: './image-property-update.component.html'
})
export class ImagePropertyUpdateComponent implements OnInit {
  isSaving: boolean;

  vipoentries: IVipoEntry[];

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    height: [null, [Validators.required]],
    width: [null, [Validators.required]],
    x: [null, [Validators.required]],
    y: [null, [Validators.required]],
    hVGColor: [],
    entryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected imagePropertyService: ImagePropertyService,
    protected vipoEntryService: VipoEntryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ imageProperty }) => {
      this.updateForm(imageProperty);
    });
    this.vipoEntryService
      .query()
      .subscribe((res: HttpResponse<IVipoEntry[]>) => (this.vipoentries = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(imageProperty: IImageProperty) {
    this.editForm.patchValue({
      id: imageProperty.id,
      label: imageProperty.label,
      height: imageProperty.height,
      width: imageProperty.width,
      x: imageProperty.x,
      y: imageProperty.y,
      hVGColor: imageProperty.hVGColor,
      entryId: imageProperty.entryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const imageProperty = this.createFromForm();
    if (imageProperty.id !== undefined) {
      this.subscribeToSaveResponse(this.imagePropertyService.update(imageProperty));
    } else {
      this.subscribeToSaveResponse(this.imagePropertyService.create(imageProperty));
    }
  }

  private createFromForm(): IImageProperty {
    return {
      ...new ImageProperty(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      height: this.editForm.get(['height']).value,
      width: this.editForm.get(['width']).value,
      x: this.editForm.get(['x']).value,
      y: this.editForm.get(['y']).value,
      hVGColor: this.editForm.get(['hVGColor']).value,
      entryId: this.editForm.get(['entryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImageProperty>>) {
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

  trackVipoEntryById(index: number, item: IVipoEntry) {
    return item.id;
  }
}
