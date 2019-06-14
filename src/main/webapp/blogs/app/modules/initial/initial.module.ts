import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogsSharedModule } from '../../shared';
import { INITIAL_ROUTE, InitialComponent } from './';

@NgModule({
  imports: [BlogsSharedModule, RouterModule.forChild([INITIAL_ROUTE])],
  declarations: [InitialComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogsInitialModule {}
