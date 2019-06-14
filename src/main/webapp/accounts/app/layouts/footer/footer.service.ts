import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class FooterService {
  footerViewed: BehaviorSubject<boolean>;

  constructor() {
    this.footerViewed = new BehaviorSubject<boolean>(true);
  }

  get isFooterViewed() {
    return this.footerViewed.asObservable();
  }

  setFooterViewed(footerViewed: boolean) {
    this.footerViewed.next(footerViewed);
  }
}
