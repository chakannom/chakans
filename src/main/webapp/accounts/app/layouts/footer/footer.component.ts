import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';
import { FooterService } from './footer.service';
import { LanguageHelper } from '../../core';

@Component({
  selector: 'cks-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.scss']
})
export class FooterComponent implements OnInit {
  isFooterViewed: Observable<boolean>;
  languages: any[];

  constructor(
    private footerService: FooterService,
    private languageHelper: LanguageHelper,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService
  ) {
    this.isFooterViewed = this.footerService.isFooterViewed;
  }

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
