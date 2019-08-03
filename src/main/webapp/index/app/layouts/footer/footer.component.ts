import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';
import { LanguageHelper } from '../../core';

@Component({
  selector: 'cks-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {
  languages: any[];

  constructor(
    private languageHelper: LanguageHelper,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService
  ) {}

  ngOnInit() {
    this.languageHelper.getAll().then(languages => {
      this.languages = languages;
    });
  }

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }
}
