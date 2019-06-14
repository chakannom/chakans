import { Pipe, Inject, LOCALE_ID, OnDestroy } from '@angular/core';
import { DatePipe } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';

@Pipe({ name: 'prettyDate', pure: false })
export class PrettyDatePipe extends DatePipe implements OnDestroy {
  private onTranslationChange: any;
  private onLangChange: any;
  private onDefaultLangChange: any;
  private langKey: string;
  private i18n: any = {
    en: { seconds_ago: 'A few seconds ago', minutes_ago: 'minute(s) ago', hours_ago: 'hour(s) ago' },
    ko: { seconds_ago: '몇 초 전', minutes_ago: '분 전', hours_ago: '시간 전' }
  };

  constructor(@Inject(LOCALE_ID) locale: string, private translate: TranslateService) {
    super(locale);
    this.langKey = this.translate.currentLang;
  }

  ngOnDestroy() {
    this.onDestroy();
  }

  transform(value: any, format?: string, timezone?: string, locale?: string): string | null {
    // if there is a subscription to onLangChange, clean it
    this.onDestroy();
    // subscribe to on*Change event
    this.onSubscribe();

    const one_minute = 60000; // 1 minute
    const nowDate = new Date();
    const srcDate = new Date(value);
    const timeAgo = Math.floor((+nowDate - +srcDate) / one_minute);

    if (timeAgo < 1) {
      // Within 1 minute
      return this.i18n[this.langKey].seconds_ago;
    } else if (timeAgo < 60) {
      // Within 1 hour
      return timeAgo + this.i18n[this.langKey].minutes_ago;
    } else if (timeAgo < 60 * 24) {
      // Within a day
      return Math.floor(timeAgo / 60) + this.i18n[this.langKey].hours_ago;
    }
    return super.transform(value, format, timezone, locale); // More than 1 day
  }

  private onSubscribe() {
    // subscribe to onTranslationChange event, in case the translations change
    if (!this.onTranslationChange) {
      this.onTranslationChange = this.translate.onTranslationChange.subscribe(event => {
        this.langKey = event.lang;
      });
    }
    // subscribe to onLangChange event, in case the language changes
    if (!this.onLangChange) {
      this.onLangChange = this.translate.onLangChange.subscribe(event => {
        this.langKey = event.lang;
      });
    }
    // subscribe to onDefaultLangChange event, in case the default language changes
    if (!this.onDefaultLangChange) {
      this.onDefaultLangChange = this.translate.onDefaultLangChange.subscribe(event => {
        this.langKey = event.lang;
      });
    }
  }

  private onDestroy() {
    if (typeof this.onTranslationChange !== 'undefined') {
      this.onTranslationChange.unsubscribe();
      this.onTranslationChange = undefined;
    }
    if (typeof this.onLangChange !== 'undefined') {
      this.onLangChange.unsubscribe();
      this.onLangChange = undefined;
    }
    if (typeof this.onDefaultLangChange !== 'undefined') {
      this.onDefaultLangChange.unsubscribe();
      this.onDefaultLangChange = undefined;
    }
  }
}
