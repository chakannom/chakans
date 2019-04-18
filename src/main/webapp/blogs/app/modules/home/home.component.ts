import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AccountService, Account } from '../../core';

@Component({
    selector: 'cks-blogs-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    constructor(private accountService: AccountService, private router: Router) {}

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            if (account) {
                this.router.navigate(['/blog.cb']);
            }
        });
    }
}
