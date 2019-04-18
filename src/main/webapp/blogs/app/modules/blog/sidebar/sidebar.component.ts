import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable } from 'rxjs';

import { IBlog, MyBlog } from '../../../core';
import { BlogSidebarService } from './sidebar.service';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'cks-blogs-blog-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['sidebar.css']
})
export class BlogSidebarComponent implements OnInit {
    isSidebarViewed: Observable<boolean>;
    blogs: IBlog[] = [];
    blogId: number;

    constructor(
        private sidebarService: BlogSidebarService,
        private myBlog: MyBlog,
        private route: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.isSidebarViewed = this.sidebarService.isSidebarViewed;
    }

    ngOnInit() {
        this.myBlog.list().then((blogs: IBlog[]) => {
            this.blogs = blogs;
        });
        this.blogId = this.route.snapshot.queryParams['blogId'];
        this.registerMyBlogsChange();
    }

    registerMyBlogsChange() {
        this.eventManager.subscribe('myBlogsChange', message => {
            this.myBlog.list().then((blogs: IBlog[]) => {
                this.blogs = blogs;
            });
        });
    }

    changeBlog(blogId: number) {
        this.blogId = blogId;
    }
}
