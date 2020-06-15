import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VipoTestModule } from '../../../test.module';
import { VipoEntryUpdateComponent } from 'app/entities/vipo-entry/vipo-entry-update.component';
import { VipoEntryService } from 'app/entities/vipo-entry/vipo-entry.service';
import { VipoEntry } from 'app/shared/model/vipo-entry.model';

describe('Component Tests', () => {
  describe('VipoEntry Management Update Component', () => {
    let comp: VipoEntryUpdateComponent;
    let fixture: ComponentFixture<VipoEntryUpdateComponent>;
    let service: VipoEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [VipoEntryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VipoEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VipoEntryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VipoEntryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VipoEntry(123);
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
        const entity = new VipoEntry();
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
