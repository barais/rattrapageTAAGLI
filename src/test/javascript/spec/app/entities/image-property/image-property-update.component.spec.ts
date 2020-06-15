import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VipoTestModule } from '../../../test.module';
import { ImagePropertyUpdateComponent } from 'app/entities/image-property/image-property-update.component';
import { ImagePropertyService } from 'app/entities/image-property/image-property.service';
import { ImageProperty } from 'app/shared/model/image-property.model';

describe('Component Tests', () => {
  describe('ImageProperty Management Update Component', () => {
    let comp: ImagePropertyUpdateComponent;
    let fixture: ComponentFixture<ImagePropertyUpdateComponent>;
    let service: ImagePropertyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [ImagePropertyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImagePropertyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImagePropertyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImagePropertyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImageProperty(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImageProperty();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
