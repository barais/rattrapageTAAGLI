import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VipoTestModule } from '../../../test.module';
import { ImagePropertyDeleteDialogComponent } from 'app/entities/image-property/image-property-delete-dialog.component';
import { ImagePropertyService } from 'app/entities/image-property/image-property.service';

describe('Component Tests', () => {
  describe('ImageProperty Management Delete Component', () => {
    let comp: ImagePropertyDeleteDialogComponent;
    let fixture: ComponentFixture<ImagePropertyDeleteDialogComponent>;
    let service: ImagePropertyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [ImagePropertyDeleteDialogComponent]
      })
        .overrideTemplate(ImagePropertyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImagePropertyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImagePropertyService);
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
