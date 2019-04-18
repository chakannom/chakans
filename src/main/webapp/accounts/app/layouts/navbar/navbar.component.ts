import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from '../../app.constants';
import { NavbarService } from './navbar.service';
import { SignInService } from '../../modules/internal/sign-in/sign-in.service';
import { LanguageHelper, AccountService } from '../../core';

@Component({
    selector: 'cks-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.css']
})
export class NavbarComponent implements OnInit {
    isNavbarViewed: Observable<boolean>;
    isNavbarCollapsed: boolean;
    languages: any[];
    version: string;
    continueUrl: string;

    constructor(
        private navbarService: NavbarService,
        private signInService: SignInService,
        private languageService: JhiLanguageService,
        private languageHelper: LanguageHelper,
        private sessionStorage: SessionStorageService,
        private accountService: AccountService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.isNavbarViewed = this.navbarService.isNavbarViewed;
    }

    ngOnInit() {
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
        this.route.queryParams.subscribe(queryParams => {
            this.continueUrl = queryParams['continue'];
        });
    }

    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    logout() {
        this.collapseNavbar();
        this.signInService.signOut();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
    }
}
