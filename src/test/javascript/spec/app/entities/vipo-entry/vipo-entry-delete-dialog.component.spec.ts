import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VipoTestModule } from '../../../test.module';
import { VipoEntryDeleteDialogComponent } from 'app/entities/vipo-entry/vipo-entry-delete-dialog.component';
import { VipoEntryService } from 'app/entities/vipo-entry/vipo-entry.service';

describe('Component Tests', () => {
  describe('VipoEntry Management Delete Component', () => {
    let comp: VipoEntryDeleteDialogComponent;
    let fixture: ComponentFixture<VipoEntryDeleteDialogComponent>;
    let service: VipoEntryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [VipoEntryDeleteDialogComponent]
      })
        .overrideTemplate(VipoEntryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VipoEntryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VipoEntryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
