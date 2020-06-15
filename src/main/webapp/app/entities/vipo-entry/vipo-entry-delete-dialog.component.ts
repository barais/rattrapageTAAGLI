import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVipoEntry } from 'app/shared/model/vipo-entry.model';
import { VipoEntryService } from './vipo-entry.service';

@Component({
  selector: 'jhi-vipo-entry-delete-dialog',
  templateUrl: './vipo-entry-delete-dialog.component.html'
})
export class VipoEntryDeleteDialogComponent {
  vipoEntry: IVipoEntry;

  constructor(protected vipoEntryService: VipoEntryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vipoEntryService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vipoEntryListModification',
        content: 'Deleted an vipoEntry'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vipo-entry-delete-popup',
  template: ''
})
export class VipoEntryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vipoEntry }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VipoEntryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vipoEntry = vipoEntry;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/vipo-entry', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/vipo-entry', { outlets: { popup: null } }]);
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
