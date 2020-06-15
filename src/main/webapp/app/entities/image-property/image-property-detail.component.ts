import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImageProperty } from 'app/shared/model/image-property.model';

@Component({
  selector: 'jhi-image-property-detail',
  templateUrl: './image-property-detail.component.html'
})
export class ImagePropertyDetailComponent implements OnInit {
  imageProperty: IImageProperty;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imageProperty }) => {
      this.imageProperty = imageProperty;
    });
  }

  previousState() {
    window.history.back();
  }
}
