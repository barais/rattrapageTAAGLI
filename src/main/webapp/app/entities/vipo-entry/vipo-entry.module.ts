import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VipoSharedModule } from 'app/shared/shared.module';
import { VipoEntryComponent } from './vipo-entry.component';
import { VipoEntryDetailComponent } from './vipo-entry-detail.component';
import { VipoEntryUpdateComponent } from './vipo-entry-update.component';
import { VipoEntryDeletePopupComponent, VipoEntryDeleteDialogComponent } from './vipo-entry-delete-dialog.component';
import { vipoEntryRoute, vipoEntryPopupRoute } from './vipo-entry.route';

const ENTITY_STATES = [...vipoEntryRoute, ...vipoEntryPopupRoute];

@NgModule({
  imports: [VipoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VipoEntryComponent,
    VipoEntryDetailComponent,
    VipoEntryUpdateComponent,
    VipoEntryDeleteDialogComponent,
    VipoEntryDeletePopupComponent
  ],
  entryComponents: [VipoEntryDeleteDialogComponent]
})
export class VipoVipoEntryModule {}
