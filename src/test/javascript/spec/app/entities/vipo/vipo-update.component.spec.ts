import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VipoTestModule } from '../../../test.module';
import { VipoUpdateComponent } from 'app/entities/vipo/vipo-update.component';
import { VipoService } from 'app/entities/vipo/vipo.service';
import { Vipo } from 'app/shared/model/vipo.model';

describe('Component Tests', () => {
  describe('Vipo Management Update Component', () => {
    let comp: VipoUpdateComponent;
    let fixture: ComponentFixture<VipoUpdateComponent>;
    let service: VipoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [VipoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VipoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VipoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VipoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vipo(123);
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
        const entity = new Vipo();
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
