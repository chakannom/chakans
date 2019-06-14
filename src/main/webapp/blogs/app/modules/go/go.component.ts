import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';

import { AccountService, RedirectService } from '../../core';
import { NavbarService } from '../../layouts/navbar/navbar.service';
import { FooterService } from '../../layouts/footer/footer.service';

@Component({
  selector: 'cks-go',
  template: ''
})
export class GoComponent implements OnInit, OnDestroy {
  constructor(
    private navbarService: NavbarService,
    private footerService: FooterService,
    private redirectService: RedirectService,
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.navbarService.setNavbarViewed(false);
    this.footerService.setFooterViewed(false);
  }

  ngOnInit() {
    this.accountService.identity().then(account => {
      if (account) {
        this.goToPage();
      } else {
        this.redirectService.goAccountsSignIn();
      }
    });
  }

  ngOnDestroy() {
    this.navbarService.setNavbarViewed(true);
    this.footerService.setFooterViewed(true);
  }

  goToPage() {
    this.route.params.subscribe(params => {
      let path: string = null;
      const navigationExtras: NavigationExtras = {};
      switch (params['modulename']) {
        case 'startblog':
          path = '/blog.cb';
          break;
        default:
          path = '/error';
          break;
      }
      this.router.navigate([path], navigationExtras);
    });
  }
}
