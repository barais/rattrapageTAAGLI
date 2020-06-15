import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VipoTestModule } from '../../../test.module';
import { VipoEntryDetailComponent } from 'app/entities/vipo-entry/vipo-entry-detail.component';
import { VipoEntry } from 'app/shared/model/vipo-entry.model';

describe('Component Tests', () => {
  describe('VipoEntry Management Detail Component', () => {
    let comp: VipoEntryDetailComponent;
    let fixture: ComponentFixture<VipoEntryDetailComponent>;
    const route = ({ data: of({ vipoEntry: new VipoEntry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [VipoEntryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VipoEntryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VipoEntryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vipoEntry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
