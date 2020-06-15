import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { VipoSharedModule } from 'app/shared/shared.module';
import { VipoCoreModule } from 'app/core/core.module';
import { VipoAppRoutingModule } from './app-routing.module';
import { VipoHomeModule } from './home/home.module';
import { VipoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    VipoSharedModule,
    VipoCoreModule,
    VipoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    VipoEntityModule,
    VipoAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class VipoAppModule {}
