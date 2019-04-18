import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AccountService } from '../../core';

@Component({
    selector: 'cks-drives-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    constructor(private accountService: AccountService, private router: Router) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            if (account) {
                this.router.navigate(['/drive']);
            }
        });
    }
}
