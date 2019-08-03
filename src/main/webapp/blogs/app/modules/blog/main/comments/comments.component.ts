import { Component, OnInit, ElementRef, Renderer2, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks, JhiEventManager } from 'ng-jhipster';
import { CksSubscriptionManager } from 'ng-chakans';
import { CommentService, IComment } from '../../../../core';
import { ITEMS_PER_PAGE, parseFragment, BLOG_COMMENTS_SUBSCRIBERS_ID } from '../../../../shared';

@Component({
  selector: 'cks-blog-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['../main.scss', './comments.scss']
})
export class BlogCommentsComponent implements OnInit, OnDestroy {
  blogId: number;
  navLinkNodes: any;
  pageType: string;
  comments: IComment[];
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private commentService: CommentService,
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
    this.subscriptionManager.push(BLOG_COMMENTS_SUBSCRIBERS_ID, routeDataSub);
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
    this.subscriptionManager.destroy(BLOG_COMMENTS_SUBSCRIBERS_ID);
  }

  loadAll() {
    this.commentService
      .getComments(this.blogId, {
        status: this.pageType.split('/')[1],
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IComment[]>) => this.onSuccessGetComments(res.body, res.headers),
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

  spam(commentId: number) {
    const comment = { id: commentId, status: 'SPAM' };
    this.commentService
      .patchComment(this.blogId, comment, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessSpam(res.body, res.headers, { commentId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  publish(commentId: number) {
    const comment = { id: commentId, status: 'PUBLISHED' };
    this.commentService
      .patchComment(this.blogId, comment, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessPublish(res.body, res.headers, { commentId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  trash(commentId: number) {
    const comment = { id: commentId, status: 'TRASHED' };
    this.commentService
      .patchComment(this.blogId, comment, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessTrash(res.body, res.headers, { commentId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  restoreFromTrash(commentId: number) {
    this.publish(commentId);
  }

  deleteFromTrash(commentId: number) {
    this.commentService
      .deleteComment(this.blogId, commentId)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessDeleteFromTrash(res.body, res.headers, { commentId }),
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

  private onSuccessGetComments(data, headers) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = headers.get('X-Total-Count');
    this.comments = data;
  }

  private onSuccessSpam(data, headers, ext?) {
    this.deleteComment(ext.commentId);
  }

  private onSuccessPublish(data, headers, ext?) {
    this.deleteComment(ext.commentId);
  }

  private onSuccessTrash(data, headers, ext?) {
    this.deleteComment(ext.commentId);
  }

  private onSuccessDeleteFromTrash(data, headers, ext?) {
    this.deleteComment(ext.commentId);
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }

  private deleteComment(commentId) {
    const pos = this.comments.map(comment => comment.id).indexOf(commentId);
    if (pos > -1) {
      this.comments.splice(pos, 1);
    }
  }
}
