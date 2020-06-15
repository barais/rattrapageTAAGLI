import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VipoTestModule } from '../../../test.module';
import { VipoDetailComponent } from 'app/entities/vipo/vipo-detail.component';
import { Vipo } from 'app/shared/model/vipo.model';

describe('Component Tests', () => {
  describe('Vipo Management Detail Component', () => {
    let comp: VipoDetailComponent;
    let fixture: ComponentFixture<VipoDetailComponent>;
    const route = ({ data: of({ vipo: new Vipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [VipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VipoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
