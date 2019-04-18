import { Component, OnInit, OnDestroy, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute, Params, Router, NavigationEnd } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { IBlog, MyBlog } from '../../core';
import { parseFragment } from '../../shared';
import { FooterService } from '../../layouts/footer/footer.service';

@Component({
    selector: 'cks-blogs-blog',
    templateUrl: './blog.component.html',
    styleUrls: ['blog.css']
})
export class BlogComponent implements OnInit, OnDestroy {
    @ViewChild('blogMainContainer', { read: ViewContainerRef })
    blogMainContainer: ViewContainerRef;
    mainCompName: string;

    constructor(
        private footerService: FooterService,
        private componentFactoryResolver: ComponentFactoryResolver,
        private route: ActivatedRoute,
        private router: Router,
        private myBlog: MyBlog,
        private eventManager: JhiEventManager
    ) {
        this.mainCompName = '';
        this.footerService.setFooterViewed(false);
    }

    ngOnInit() {
        this.changeMainViewContainer();

        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.changeMainViewContainer();
            }
        });
    }

    ngOnDestroy() {
        this.footerService.setFooterViewed(true);
    }

    private changeMainViewContainer() {
        this.myBlog.list().then(blogs => {
            const fragment = this.route.snapshot.fragment;
            // const fragmentParams = parseFragment(this.route.snapshot.fragment);
            // console.log(fragmentParams)
            const mainCompsType = this.mainCompsType(blogs.length);
            if (
                (mainCompsType === 'existblog' && this.route.snapshot.queryParams['blogId'] === undefined) ||
                fragment === null ||
                fragment === undefined ||
                Object.keys(this.route.snapshot.data.mainComps[mainCompsType]).indexOf(fragment.split('/')[0]) < 0
            ) {
                const blogId = this.defaultBlogId(blogs);
                const defaultFragment = this.defaultFragment(blogs.length);
                this.router.navigate(['/blog.cb'], { queryParams: { blogId: blogId }, fragment: defaultFragment });
                return;
            }
            if (fragment !== this.mainCompName) {
                this.mainCompName = fragment.split('/')[0];
                const mainComp = this.route.snapshot.data.mainComps[mainCompsType][this.mainCompName];
                const mainCompFactory = this.componentFactoryResolver.resolveComponentFactory(mainComp);
                this.blogMainContainer.clear();
                this.blogMainContainer.createComponent(mainCompFactory);
            }
        });
    }

    private mainCompsType(blogCount: number) {
        return blogCount > 0 ? 'existblog' : 'noblog';
    }

    private defaultBlogId(blogs: IBlog[]) {
        const blogId = this.route.snapshot.queryParams['blogId'];
        if (blogs.length > 0) {
            if (blogId === undefined || blogId === '') {
                return blogs[0].id;
            }
            return blogId;
        }
        return undefined;
    }

    private defaultFragment(blogCount: number) {
        return blogCount > 0 ? 'posts/published' : 'welcome';
    }
}
