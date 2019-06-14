import { NgModule } from '@angular/core';

import { BlogsSharedLibsModule } from '../../../shared';
import { PopoverComponent } from './';

@NgModule({
  imports: [BlogsSharedLibsModule],
  declarations: [PopoverComponent],
  exports: [PopoverComponent]
})
export class BlogCommonModule {}
