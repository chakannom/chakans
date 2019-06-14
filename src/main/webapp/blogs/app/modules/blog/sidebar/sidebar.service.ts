import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { BlogsBlogModule } from '../blog.module';

@Injectable({ providedIn: BlogsBlogModule })
export class BlogSidebarService {
  sidebarViewed: BehaviorSubject<boolean>;

  constructor() {
    this.sidebarViewed = new BehaviorSubject<boolean>(true);
  }

  get isSidebarViewed() {
    return this.sidebarViewed.asObservable();
  }

  setSidebarViewed(sidebarViewed: boolean) {
    this.sidebarViewed.next(sidebarViewed);
  }
}
