import { Component } from '@angular/core';
import { Observable } from 'rxjs';

import { FooterService } from './footer.service';

@Component({
    selector: 'cks-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent {
    isFooterViewed: Observable<boolean>;

    constructor(
        private footerService: FooterService
    ) {
        this.isFooterViewed = this.footerService.isFooterViewed;
    }
}
