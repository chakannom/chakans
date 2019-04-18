import { Component, OnInit, AfterViewInit, Renderer2, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';

import { RedirectService } from '../../../core';

@Component({
    selector: 'cks-sign-up-result',
    templateUrl: './sign-up-result.component.html'
})
export class SignUpResultComponent implements OnInit, AfterViewInit {
    success: boolean;
    error: boolean;

    constructor(
        private languageService: JhiLanguageService,
        private redirectService: RedirectService,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.success = params['result'] === 'success';
            this.error = params['result'] === 'error';
        });
    }

    ngAfterViewInit() {}
}
