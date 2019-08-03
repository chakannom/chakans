import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { Observable } from 'rxjs';
import { LanguageHelper } from '../../core';
import { MainService } from './main.service';

@Component({
  selector: 'cks-main',
  templateUrl: './main.component.html'
})
export class MainComponent implements OnInit {
  containerClasses: string[];

  constructor(private mainService: MainService, private languageHelper: LanguageHelper, private router: Router) {
    this.containerClasses = this.mainService.getContainerClasses();
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'accountsApp';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.languageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
  }
}
