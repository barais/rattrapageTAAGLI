import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VipoSharedModule } from 'app/shared/shared.module';
import { ImagePropertyComponent } from './image-property.component';
import { ImagePropertyDetailComponent } from './image-property-detail.component';
import { ImagePropertyUpdateComponent } from './image-property-update.component';
import { ImagePropertyDeletePopupComponent, ImagePropertyDeleteDialogComponent } from './image-property-delete-dialog.component';
import { imagePropertyRoute, imagePropertyPopupRoute } from './image-property.route';

const ENTITY_STATES = [...imagePropertyRoute, ...imagePropertyPopupRoute];

@NgModule({
  imports: [VipoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImagePropertyComponent,
    ImagePropertyDetailComponent,
    ImagePropertyUpdateComponent,
    ImagePropertyDeleteDialogComponent,
    ImagePropertyDeletePopupComponent
  ],
  entryComponents: [ImagePropertyDeleteDialogComponent]
})
export class VipoImagePropertyModule {}
