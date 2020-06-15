import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImageProperty } from 'app/shared/model/image-property.model';
import { ImagePropertyService } from './image-property.service';

@Component({
  selector: 'jhi-image-property-delete-dialog',
  templateUrl: './image-property-delete-dialog.component.html'
})
export class ImagePropertyDeleteDialogComponent {
  imageProperty: IImageProperty;

  constructor(
    protected imagePropertyService: ImagePropertyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.imagePropertyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'imagePropertyListModification',
        content: 'Deleted an imageProperty'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-image-property-delete-popup',
  template: ''
})
export class ImagePropertyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imageProperty }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImagePropertyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.imageProperty = imageProperty;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/image-property', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/image-property', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
