import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVipoEntry } from 'app/shared/model/vipo-entry.model';

@Component({
  selector: 'jhi-vipo-entry-detail',
  templateUrl: './vipo-entry-detail.component.html'
})
export class VipoEntryDetailComponent implements OnInit {
  vipoEntry: IVipoEntry;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vipoEntry }) => {
      this.vipoEntry = vipoEntry;
    });
  }

  previousState() {
    window.history.back();
  }
}
