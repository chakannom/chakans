import { Component, OnInit, OnDestroy } from '@angular/core';

import { FooterService } from '../../layouts/footer/footer.service';

@Component({
    selector: 'cks-drives-blog',
    templateUrl: './drive.component.html',
    styleUrls: ['drive.css']
})
export class DriveComponent implements OnInit, OnDestroy {
    constructor(private footerService: FooterService) {
        this.footerService.setFooterViewed(false);
    }

    ngOnInit() {}

    ngOnDestroy() {
        this.footerService.setFooterViewed(true);
    }
}
