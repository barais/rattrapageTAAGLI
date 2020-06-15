import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'store',
        loadChildren: () => import('./store/store.module').then(m => m.VipoStoreModule)
      },
      {
        path: 'vipo',
        loadChildren: () => import('./vipo/vipo.module').then(m => m.VipoVipoModule)
      },
      {
        path: 'vipo-entry',
        loadChildren: () => import('./vipo-entry/vipo-entry.module').then(m => m.VipoVipoEntryModule)
      },
      {
        path: 'image-property',
        loadChildren: () => import('./image-property/image-property.module').then(m => m.VipoImagePropertyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class VipoEntityModule {}
