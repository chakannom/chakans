import { Component, OnInit, OnDestroy, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';
import { CksSubscriptionManager } from 'ng-chakans';
import { BlogService, IBlog } from '../../core';
import { FooterService, MainService } from '../../layouts';
import { SIDEBAR_MENU, BLOG_MAIN_SUBSCRIBERS_ID } from '../../shared';

@Component({
  selector: 'cks-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['blog.scss']
})
export class BlogComponent implements OnInit, OnDestroy {
  @ViewChild('blogMainContainer', { read: ViewContainerRef, static: true })
  private blogMainContainer: ViewContainerRef;
  menuItems: any[];
  blogs: IBlog[];
  blogId: number;

  constructor(
    private blogService: BlogService,
    private mainService: MainService,
    private footerService: FooterService,
    private route: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager,
    private subscriptionManager: CksSubscriptionManager,
    private componentFactoryResolver: ComponentFactoryResolver
  ) {
    const resolveSubscriber = this.route.data.subscribe(data => {
      this.blogId = data['blogIdParam'];
      this.menuItems = SIDEBAR_MENU(this.blogId);
    });
    this.subscriptionManager.push(BLOG_MAIN_SUBSCRIBERS_ID, resolveSubscriber);
    this.mainService.addContainerClass('cks-container-with-sidebar');
    this.footerService.setFooterViewed(false);
  }

  ngOnInit() {
    this.blogService.list().then((blogs: IBlog[]) => {
      this.blogs = blogs;
    });
    this.registerBlogsChangeEvent();
    this.changeMainViewContainer();
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => this.changeMainViewContainer());
  }

  ngOnDestroy() {
    this.subscriptionManager.destroy(BLOG_MAIN_SUBSCRIBERS_ID);
    this.mainService.removeContainerClass('cks-container-with-sidebar');
    this.footerService.setFooterViewed(true);
  }

  changeBlog(blogId: number) {
    this.blogId = blogId;
    this.menuItems = SIDEBAR_MENU(this.blogId);
  }

  private registerBlogsChangeEvent() {
    const subscriber = this.eventManager.subscribe('blogsChanged', message => {
      this.blogService.list().then((blogs: IBlog[]) => {
        this.blogs = blogs;
      });
    });
    this.subscriptionManager.push(BLOG_MAIN_SUBSCRIBERS_ID, subscriber);
  }

  private changeMainViewContainer() {
    this.blogService.list().then(blogs => {
      const fragment = this.route.snapshot.fragment;
      const componentType = blogs.length > 0 ? 'existBlog' : 'emptyBlog';
      const componentName = Object.keys(this.route.snapshot.data.mainComponents[componentType]).find(
        compName => fragment && compName === fragment.split('/')[0]
      );
      if ((blogs.length > 0 && !this.route.snapshot.queryParams['blogId']) || !componentName) {
        const defaultBlogId = blogs.length > 0 ? blogs[0].id : null;
        const defaultFragment = blogs.length > 0 ? 'posts/published' : 'welcome';
        this.router.navigate(['/blog.cb'], { queryParams: { blogId: defaultBlogId }, fragment: defaultFragment });
        return false;
      }
      const mainComp = this.route.snapshot.data.mainComponents[componentType][componentName];
      const mainCompFactory = this.componentFactoryResolver.resolveComponentFactory(mainComp);
      this.blogMainContainer.clear();
      this.blogMainContainer.createComponent(mainCompFactory);
    });
  }
}
