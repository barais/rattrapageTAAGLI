import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VipoTestModule } from '../../../test.module';
import { ImagePropertyDetailComponent } from 'app/entities/image-property/image-property-detail.component';
import { ImageProperty } from 'app/shared/model/image-property.model';

describe('Component Tests', () => {
  describe('ImageProperty Management Detail Component', () => {
    let comp: ImagePropertyDetailComponent;
    let fixture: ComponentFixture<ImagePropertyDetailComponent>;
    const route = ({ data: of({ imageProperty: new ImageProperty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VipoTestModule],
        declarations: [ImagePropertyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImagePropertyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImagePropertyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imageProperty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
