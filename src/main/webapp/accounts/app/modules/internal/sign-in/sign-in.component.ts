import { Component, OnInit, AfterViewInit, OnDestroy, Renderer2 } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { CksNavbarService, CksRouteService } from 'ng-chakans';
import { SessionStorageService } from 'ngx-webstorage';

import { LanguageHelper, SignInService } from '../../../core';
import { FooterService } from '../../../layouts';

@Component({
  selector: 'cks-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['sign-in.scss']
})
export class SignInComponent implements OnInit, AfterViewInit, OnDestroy {
  languages: any[];
  authenticationError: boolean;
  continueUrl: string;
  signInForm = this.fb.group({
    email: [''],
    password: [''],
    rememberMe: [false]
  });

  constructor(
    private signInService: SignInService,
    private footerService: FooterService,
    private languageService: JhiLanguageService,
    private navbarService: CksNavbarService,
    private routeService: CksRouteService,
    private languageHelper: LanguageHelper,
    private sessionStorage: SessionStorageService,
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.navbarService.setNavbarViewed(false);
    this.footerService.setFooterViewed(false);
  }

  ngOnInit() {
    this.signInForm.patchValue({ email: 'user@localhost', password: 'user' });
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

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  signIn() {
    this.signInService
      .signIn({
        username: this.signInForm.get(['email']).value,
        password: this.signInForm.get(['password']).value,
        rememberMe: this.signInForm.get(['rememberMe']).value
      })
      .then(() => {
        this.authenticationError = false;
        if (this.router.url === '/signup' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
          this.router.navigate(['']);
        }

        this.routeService.navigate(this.continueUrl);
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
