import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService, SignOutService } from '../../core';
import { VERSION } from '../../app.constants';

@Component({
  selector: '[cks-navbar]',
  template: `
    <cks-navbar [brand]="brand" [menuItems]="menuItems"></cks-navbar>
  `
})
export class NavbarComponent implements OnInit {
  brand: any;
  menuItems: any[];

  constructor(private accountService: AccountService, private signOutService: SignOutService, private router: Router) {}

  ngOnInit() {
    this.brand = this.getBrand();
    this.menuItems = this.getMenuItems();
    this.accountService.getAuthenticationState().subscribe(identity => {
      this.menuItems = this.getMenuItems();
    });
  }

  private getBrand() {
    return {
      title: {
        label: 'Chakans',
        translateKey: 'global.title'
      },
      version: VERSION ? 'v' + VERSION : ''
    };
  }

  private getMenuItems() {
    if (this.accountService.isAuthenticated()) {
      return this.getAuthenticatedMenuItems();
    }
    return this.getUnAuthenticatedMenuItems();
  }

  private getUnAuthenticatedMenuItems() {
    return [
      {
        navigation: '/accounts/signin?continue=' + encodeURIComponent(window.location.href),
        icon: ['fa', 'sign-in-alt'],
        name: {
          label: 'Sign in',
          translateKey: 'global.menu.sign.in'
        }
      }
    ];
  }

  private getAuthenticatedMenuItems() {
    return [
      {
        id: 'my-account-menu',
        icon: ['fa', 'user'],
        name: {
          label: 'Account',
          translateKey: 'global.menu.account.main'
        },
        subItems: [
          {
            navigation: {
              routerLink: ['my/settings']
            },
            icon: ['fa', 'wrench'],
            name: {
              label: 'Settings',
              translateKey: 'global.menu.account.settings'
            }
          },
          {
            navigation: {
              routerLink: ['my/password/change']
            },
            icon: ['fa', 'lock'],
            name: {
              label: 'Password',
              translateKey: 'global.menu.account.password'
            }
          },
          {
            navigation: this.signOut,
            icon: ['fa', 'sign-out-alt'],
            name: {
              label: 'Sign out',
              translateKey: 'global.menu.account.sign.out'
            }
          }
        ]
      }
    ];
  }

  private signOut = () => {
    this.signOutService.signOut();
    this.router.navigate(['']);
  };
}
