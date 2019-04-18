import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DrivesSharedModule } from '../../shared';

import { DRIVE_ROUTE, DriveComponent, DriveSidebarComponent, DriveToolbarComponent } from './';

@NgModule({
    imports: [DrivesSharedModule, RouterModule.forChild([DRIVE_ROUTE])],
    declarations: [DriveComponent, DriveSidebarComponent, DriveToolbarComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DrivesDriveModule {}
