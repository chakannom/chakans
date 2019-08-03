import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { CksSidebarService, CksSubscriptionManager } from 'ng-chakans';
import { ITheme, ThemeService, LanguageHelper, IBlog, BlogService } from '../../../../core';
import { BLANK_IMAGE, createImgproxySignatureUrl, BLOG_MAKER_SUBSCRIBERS_ID } from '../../../../shared';

@Component({
  selector: 'cks-blog-maker',
  templateUrl: './maker.component.html',
  styleUrls: ['../main.scss', './maker.scss']
})
export class BlogMakerComponent implements OnInit, OnDestroy {
  langKey: string;
  themes: ITheme[];
  currTheme: ITheme;
  isEnteringSubdomain: boolean;
  isAvailableSubdomain: boolean;
  makerForm = this.fb.group({
    title: ['', [Validators.minLength(1), Validators.maxLength(100), Validators.required]],
    subDomain: ['', [Validators.maxLength(100), Validators.pattern('^([A-Za-z0-9][A-Za-z0-9-]*)*[A-Za-z0-9]$')]]
  });

  constructor(
    private languageHelper: LanguageHelper,
    private themeService: ThemeService,
    private blogService: BlogService,
    private alertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private sidebarService: CksSidebarService,
    private subscriptionManager: CksSubscriptionManager,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.sidebarService.setSidebarViewed(false);
  }

  ngOnInit() {
    this.isEnteringSubdomain = false;
    this.isAvailableSubdomain = false;
    const languageSubscription = this.languageHelper.language.subscribe((langKey: string) => {
      this.langKey = langKey;
      this.themeService
        .getThemes(this.langKey)
        .subscribe(
          (res: HttpResponse<ITheme[]>) => this.onSuccessGetThemes(res.body, res.headers),
          (res: HttpResponse<any>) => this.onError(res.body)
        );
    });
    this.subscriptionManager.push(BLOG_MAKER_SUBSCRIBERS_ID, languageSubscription);
  }

  ngOnDestroy() {
    this.subscriptionManager.destroy(BLOG_MAKER_SUBSCRIBERS_ID);
    this.sidebarService.setSidebarViewed(true);
  }

  enterSubdomain() {
    this.isEnteringSubdomain = true;
    this.isAvailableSubdomain = false;
  }

  checkSubdomain(subdomain) {
    this.isEnteringSubdomain = false;
    if (subdomain && subdomain.length > 1) {
      this.blogService.checkSubdomainAvailability(subdomain).subscribe(res => {
        this.isAvailableSubdomain = res.body.availability;
      });
    }
  }

  getThumbnailImageUrl(imageUrl) {
    if (imageUrl) {
      return createImgproxySignatureUrl('fit', 143, 90, 'ce', 0, imageUrl, 'png');
    }
    return BLANK_IMAGE;
  }

  previewTheme(themeId) {
    this.currTheme = this.themes.find(theme => theme.id === themeId);
  }

  getPreviewImageUrl(imageUrl) {
    if (imageUrl) {
      return createImgproxySignatureUrl('fit', 450, 283, 'ce', 0, imageUrl, 'png');
    }
    return BLANK_IMAGE;
  }

  create() {
    const blog = {
      themeId: this.currTheme.id,
      title: this.makerForm.get(['title']).value,
      subdomain: this.makerForm.get(['subDomain']).value
    };
    this.blogService
      .createBlog(blog)
      .subscribe(
        (res: HttpResponse<IBlog>) => this.onSuccessCreateBlog(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  private onSuccessGetThemes(data, headers) {
    this.themes = data;
    if (this.themes.length > 0) {
      this.previewTheme(this.themes[0].id);
    }
  }

  private onSuccessCreateBlog(data, headers) {
    this.blogService.list(true).then(blogs => {
      this.eventManager.broadcast({
        name: 'blogsChanged',
        content: 'Sending Blogs Changed'
      });
      this.router.navigate(['/blog.cb'], { queryParams: { blogId: blogs[blogs.length - 1].id }, fragment: 'posts/published' });
    });
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }
}
