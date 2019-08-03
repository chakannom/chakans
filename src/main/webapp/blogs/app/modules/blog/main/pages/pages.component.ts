import { Component, OnInit, ElementRef, Renderer2, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks, JhiEventManager } from 'ng-jhipster';
import { CksSubscriptionManager } from 'ng-chakans';
import { PageService, IPage } from '../../../../core';
import { ITEMS_PER_PAGE, parseFragment, BLOG_PAGES_SUBSCRIBERS_ID } from '../../../../shared';

@Component({
  selector: 'cks-blog-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['../main.scss', './pages.scss']
})
export class BlogPagesComponent implements OnInit, OnDestroy {
  blogId: number;
  navLinkNodes: any;
  pageType: string;
  rowNum: number;
  pages: IPage[];
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private pageService: PageService,
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
    this.subscriptionManager.push(BLOG_PAGES_SUBSCRIBERS_ID, routeDataSub);
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
    this.subscriptionManager.destroy(BLOG_PAGES_SUBSCRIBERS_ID);
  }

  loadAll() {
    this.pageService
      .getPages(this.blogId, {
        status: this.pageType.split('/')[1],
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPage[]>) => this.onSuccessGetPages(res.body, res.headers),
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

  moveToDraft(pageId: number) {
    const page = { id: pageId, status: 'DRAFT' };
    this.pageService
      .patchPage(this.blogId, page, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessMoveToDraft(res.body, res.headers, { pageId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  publish(pageId: number) {
    const page = { id: pageId, status: 'PUBLISHED' };
    this.pageService
      .patchPage(this.blogId, page, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessPublish(res.body, res.headers, { pageId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  trash(pageId: number) {
    const page = { id: pageId, status: 'TRASHED' };
    this.pageService
      .patchPage(this.blogId, page, ['status'])
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessTrash(res.body, res.headers, { pageId }),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  restoreFromTrash(pageId: number) {
    this.moveToDraft(pageId);
  }

  deleteFromTrash(pageId: number) {
    this.pageService
      .deletePage(this.blogId, pageId)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessDeleteFromTrash(res.body, res.headers, { pageId }),
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

  private onSuccessGetPages(data, headers) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = headers.get('X-Total-Count');
    this.pages = data;
  }

  private onSuccessMoveToDraft(data, headers, ext?) {
    this.deletePage(ext.pageId);
  }

  private onSuccessPublish(data, headers, ext?) {
    this.deletePage(ext.pageId);
  }

  private onSuccessTrash(data, headers, ext?) {
    this.deletePage(ext.pageId);
  }

  private onSuccessDeleteFromTrash(data, headers, ext?) {
    this.deletePage(ext.pageId);
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }

  private deletePage(pageId) {
    const pos = this.pages.map(page => page.id).indexOf(pageId);
    if (pos > -1) {
      this.pages.splice(pos, 1);
    }
  }
}
