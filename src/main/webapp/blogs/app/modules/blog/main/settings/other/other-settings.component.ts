import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'cks-blog-other-settings',
  templateUrl: './other-settings.component.html',
  styleUrls: ['../../main.scss', '../settings.scss']
})
export class BlogOtherSettingsComponent implements OnInit {
  blogId: number;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.blogId = this.route.snapshot.queryParams['blogId'];
  }
}
