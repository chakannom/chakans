import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { BlogSidebarService } from '../../sidebar/sidebar.service';
import { ITheme, ThemeService, LanguageHelper, BlogService, IBlog, MyBlog } from '../../../../core';
import { BLANK_IMAGE, createImgproxySignatureUrl } from '../../../../shared';

@Component({
    selector: 'cks-blogs-blog-maker',
    templateUrl: './maker.component.html',
    styleUrls: ['../main.css', './maker.css']
})
export class BlogMakerComponent implements OnInit, OnDestroy {
    langKey: string;
    themes: ITheme[];
    currTheme: ITheme;
    isEnteringSubdomain: boolean;
    isAvailableSubdomain: boolean;
    blog: any;
    subscribes: Subscription[] = [];

    constructor(
        private languageHelper: LanguageHelper,
        private sidebarService: BlogSidebarService,
        private themeService: ThemeService,
        private blogService: BlogService,
        private myBlog: MyBlog,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private router: Router
    ) {
        this.sidebarService.setSidebarViewed(false);
    }

    ngOnInit() {
        this.isEnteringSubdomain = false;
        this.isAvailableSubdomain = false;
        this.blog = {};
        const languageSub = this.languageHelper.language.subscribe((langKey: string) => {
            this.langKey = langKey;
            this.themeService
                .getThemes(this.langKey)
                .subscribe(
                    (res: HttpResponse<ITheme[]>) => this.onSuccessGetThemes(res.body, res.headers),
                    (res: HttpResponse<any>) => this.onError(res.body)
                );
        });
        this.subscribes.push(languageSub);
    }

    ngOnDestroy() {
        this.sidebarService.setSidebarViewed(true);
        this.subscribes.forEach(subscribe => subscribe.unsubscribe());
        this.subscribes.splice(0, this.subscribes.length);
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
        this.themeService
            .getTheme(themeId, this.langKey)
            .subscribe(
                (res: HttpResponse<ITheme>) => this.onSuccessGetTheme(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    getPreviewImageUrl(imageUrl) {
        if (imageUrl) {
            return createImgproxySignatureUrl('fit', 450, 283, 'ce', 0, imageUrl, 'png');
        }
        return BLANK_IMAGE;
    }

    create() {
        this.blogService
            .createBlog(this.blog)
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

    private onSuccessGetTheme(data, headers) {
        this.currTheme = data;
        this.blog.themeId = this.currTheme.id;
    }

    private onSuccessCreateBlog(data, headers) {
        this.myBlog.push(data);
        this.eventManager.broadcast({
            name: 'myBlogsChange',
            content: 'Sending My Blogs Change'
        });
        this.router.navigate(['/blog.cb'], { queryParams: { blogId: data.id }, fragment: 'posts/published' });
    }

    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }
}
