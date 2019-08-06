import { Component, OnInit, ElementRef, Renderer2, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks, JhiEventManager } from 'ng-jhipster';
import { CksSubscriptionManager } from 'ng-chakans';
import { PostService, IPost } from '../../../../core';
import { ITEMS_PER_PAGE, parseFragment, BLOG_POSTS_SUBSCRIBERS_ID } from '../../../../shared';

@Component({
  selector: 'cks-blog-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['../main.scss', './posts.scss']
})
export class BlogPostsComponent implements OnInit, OnDestroy {
  blogId: number;
  navLinkNodes: any;
  pageType: string;
  rowNum: number;
  posts: IPost[];
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private postService: PostService,
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private router: Router,
    private route: ActivatedRoute,
    private alertService: JhiAlertService,
    private parseLinks: JhiParseLinks,
    private subscriptionManager: CksSubscriptionManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    const routeDataSub = this.route.data.subscribe(data => {
      this.page = data['pagingParams'].page;
      this.previousPage = data['pagingParams'].page;
      this.reverse = data['pagingParams'].ascending;
      this.predicate = data['pagingParams'].predicate;
    });
    this.subscriptionManager.push(BLOG_POSTS_SUBSCRIBERS_ID, routeDataSub);
  }

  ngOnInit() {
    this.blogId = this.route.snapshot.queryParams['blogId'];
    this.navLinkNodes = this.elementRef.nativeElement.querySelectorAll('.tabs .nav-item .nav-link');
    const fragmentParams = parseFragment(this.route.snapshot.fragment);
    this.pageType = Object.keys(fragmentParams)[0];
    this.changeRouterLinkActive(this.pageType);
    this.loadAll();
  }

  ngOnDestroy() {
    this.subscriptionManager.destroy(BLOG_POSTS_SUBSCRIBERS_ID);
  }

  loadAll() {
    this.postService
      .getPosts(this.blogId, {
        status: this.pageType.split('/')[1],
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPost[]>) => this.onSuccessGetPosts(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  moveToDraft(postId: number) {
    const post = { id: postId, status: 'DRAFT' };
    this.postService
      .patchPost(this.blogId, post, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessMoveToDraft(res.body, res.headers, { postId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  publish(postId: number) {
    const post = { id: postId, status: 'PUBLISHED' };
    this.postService
      .patchPost(this.blogId, post, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessPublish(res.body, res.headers, { postId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  trash(postId: number) {
    const post = { id: postId, status: 'TRASHED' };
    this.postService
      .patchPost(this.blogId, post, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessTrash(res.body, res.headers, { postId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  restoreFromTrash(postId: number) {
    this.moveToDraft(postId);
  }

  deleteFromTrash(postId: number) {
    this.postService
      .deletePost(this.blogId, postId)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessDeleteFromTrash(res.body, res.headers, { postId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  private changeRouterLinkActive(pageType: string): void {
    for (let i = 0; i < this.navLinkNodes.length; i++) {
      const navLinkElement = this.navLinkNodes[i];
      if (navLinkElement.getAttribute('fragment') === pageType) {
        this.renderer.addClass(navLinkElement.parentNode, 'active');
      } else {
        this.renderer.removeClass(navLinkElement.parentNode, 'active');
      }
    }
  }

  private onSuccessGetPosts(data, headers) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = headers.get('X-Total-Count');
    this.posts = data;
  }

  private onSuccessMoveToDraft(data, headers, ext?) {
    this.deletePost(ext.postId);
  }

  private onSuccessPublish(data, headers, ext?) {
    this.deletePost(ext.postId);
  }

  private onSuccessTrash(data, headers, ext?) {
    this.deletePost(ext.postId);
  }

  private onSuccessDeleteFromTrash(data, headers, ext?) {
    this.deletePost(ext.postId);
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }

  private deletePost(postId) {
    const pos = this.posts.map(post => post.id).indexOf(postId);
    console.log(pos);
    if (pos > -1) {
      this.posts.splice(pos, 1);
    }
  }
}
