import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVipo } from 'app/shared/model/vipo.model';
import { VipoService } from './vipo.service';

@Component({
  selector: 'jhi-vipo-delete-dialog',
  templateUrl: './vipo-delete-dialog.component.html'
})
export class VipoDeleteDialogComponent {
  vipo: IVipo;

  constructor(protected vipoService: VipoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vipoListModification',
        content: 'Deleted an vipo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vipo-delete-popup',
  template: ''
})
export class VipoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vipo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VipoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vipo = vipo;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/vipo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/vipo', { outlets: { popup: null } }]);
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
