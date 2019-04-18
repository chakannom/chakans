import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable } from 'rxjs';

import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'cks-drives-drive-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['sidebar.css']
})
export class DriveSidebarComponent implements OnInit {
    directories = [
        {
            name: 'Sub Folder1',
            directories_count: 0,
            directories: []
        },
        {
            name: 'Sub Folder2',
            directories_count: 2,
            directories: [
                {
                    name: 'Sub Folder2-1',
                    directories_count: 0,
                    directories: []
                },
                {
                    name: 'Sub Folder2-2',
                    directories_count: 0,
                    directories: []
                }
            ]
        }
    ];

    constructor() {}

    ngOnInit() {}

    directoryClick() {
        console.log('test');
    }
}
