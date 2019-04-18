import { Component, OnInit, AfterViewInit, OnDestroy, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { LanguageHelper, RedirectService } from '../../../core';
import { SignInService } from './sign-in.service';
import { NavbarService } from '../../../layouts/navbar/navbar.service';
import { FooterService } from '../../../layouts/footer/footer.service';

@Component({
    selector: 'cks-sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['sign-in.css']
})
export class SignInComponent implements OnInit, AfterViewInit, OnDestroy {
    languages: any[];
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    email: string;
    credentials: any;
    continueUrl: string;

    constructor(
        private signInService: SignInService,
        private navbarService: NavbarService,
        private footerService: FooterService,
        private redirectService: RedirectService,
        private languageService: JhiLanguageService,
        private languageHelper: LanguageHelper,
        private sessionStorage: SessionStorageService,
        private translateService: TranslateService,
        private renderer: Renderer2,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.credentials = {};
        this.navbarService.setNavbarViewed(false);
        this.footerService.setFooterViewed(false);
    }

    ngOnInit() {
        this.email = 'user@localhost';
        this.password = 'user';
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
        this.route.queryParams.subscribe(queryParams => {
            this.continueUrl = queryParams['continue'];
        });
    }

    ngAfterViewInit() {
        setTimeout(() => this.renderer.selectRootElement('#email').focus(), 0);
    }

    ngOnDestroy() {
        this.navbarService.setNavbarViewed(true);
        this.footerService.setFooterViewed(true);
    }

    updateLanguageActiveMenu(languageKey: string): boolean {
        return this.translateService.currentLang === languageKey;
    }

    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    signIn() {
        this.signInService
            .signIn({
                username: this.email,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                if (this.router.url === '/signup' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }

                this.redirectService.go(this.continueUrl);
            })
            .catch(() => {
                this.authenticationError = true;
            });
    }

    signUp() {
        this.router.navigate(['/signup'], { queryParams: { continue: this.continueUrl } });
    }

    initResetPassword() {
        this.router.navigate(['/password/reset/init']);
    }
}
