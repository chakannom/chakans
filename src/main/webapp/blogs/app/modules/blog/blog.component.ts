import { Component, OnInit, OnDestroy, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

import { BlogService } from '../../core';
import { FooterService } from '../../layouts/footer/footer.service';

@Component({
  selector: 'cks-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['blog.scss']
})
export class BlogComponent implements OnInit, OnDestroy {
  @ViewChild('blogMainContainer', { static: true })
  private blogMainContainer: ViewContainerRef;
  private subscriptions: Subscription[];
  private blogId: number;

  constructor(
    private blogService: BlogService,
    private footerService: FooterService,
    private route: ActivatedRoute,
    private router: Router,
    private componentFactoryResolver: ComponentFactoryResolver
  ) {
    const resolveSubscription = this.route.data.subscribe(data => {
      this.blogId = data['blogIdParam'];
    });
    this.subscriptions = [resolveSubscription];
    this.footerService.setFooterViewed(false);
  }

  ngOnInit() {
    this.changeMainViewContainer();
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => this.changeMainViewContainer());
  }

  ngOnDestroy() {
    this.subscriptions.filter(subscription => subscription).forEach(subscription => subscription.unsubscribe());
    this.subscriptions.splice(0, this.subscriptions.length);
    this.footerService.setFooterViewed(true);
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
