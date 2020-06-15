import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VipoSharedModule } from 'app/shared/shared.module';
import { VipoComponent } from './vipo.component';
import { VipoDetailComponent } from './vipo-detail.component';
import { VipoUpdateComponent } from './vipo-update.component';
import { VipoDeletePopupComponent, VipoDeleteDialogComponent } from './vipo-delete-dialog.component';
import { vipoRoute, vipoPopupRoute } from './vipo.route';

const ENTITY_STATES = [...vipoRoute, ...vipoPopupRoute];

@NgModule({
  imports: [VipoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VipoComponent, VipoDetailComponent, VipoUpdateComponent, VipoDeleteDialogComponent, VipoDeletePopupComponent],
  entryComponents: [VipoDeleteDialogComponent]
})
export class VipoVipoModule {}
