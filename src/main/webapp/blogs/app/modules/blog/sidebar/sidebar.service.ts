import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
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
