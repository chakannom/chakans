import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BlogService, IBlog } from '../../../core';
import { BlogSidebarService } from './sidebar.service';

@Component({
  selector: 'cks-blog-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['sidebar.scss']
})
export class BlogSidebarComponent implements OnInit {
  isSidebarViewed: Observable<boolean>;
  blogs: IBlog[];
  blogId: number;

  constructor(
    private blogService: BlogService,
    private sidebarService: BlogSidebarService,
    private route: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager
  ) {
    this.isSidebarViewed = this.sidebarService.isSidebarViewed;
    this.blogs = [];
  }

  ngOnInit() {
    this.blogService.list().then(blogs => {
      this.blogs = blogs;
    });
    this.blogId = this.route.snapshot.queryParams['blogId'];
    this.registerBlogsChangeEvent();
  }

  registerBlogsChangeEvent() {
    this.eventManager.subscribe('blogsChange', message => {
      this.blogService.list().then(blogs => {
        this.blogs = blogs;
      });
    });
  }

  changeBlog(blogId: number) {
    this.blogId = blogId;
  }
}
