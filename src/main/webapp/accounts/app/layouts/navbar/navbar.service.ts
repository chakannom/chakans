import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class NavbarService {
  navbarViewed: BehaviorSubject<boolean>;

  constructor() {
    this.navbarViewed = new BehaviorSubject<boolean>(true);
  }

  get isNavbarViewed() {
    return this.navbarViewed.asObservable();
  }

  setNavbarViewed(navbarViewed: boolean) {
    this.navbarViewed.next(navbarViewed);
  }
}
