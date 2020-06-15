import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVipo } from 'app/shared/model/vipo.model';

@Component({
  selector: 'jhi-vipo-detail',
  templateUrl: './vipo-detail.component.html'
})
export class VipoDetailComponent implements OnInit {
  vipo: IVipo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vipo }) => {
      this.vipo = vipo;
    });
  }

  previousState() {
    window.history.back();
  }
}
