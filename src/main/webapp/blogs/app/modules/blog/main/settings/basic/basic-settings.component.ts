import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { BlogService, IBlog } from '../../../../../core';

@Component({
    selector: 'cks-blogs-blog-basic-settings',
    templateUrl: './basic-settings.component.html',
    styleUrls: ['../../main.css', '../settings.css', './basic-settings.css']
})
export class BlogBasicSettingsComponent implements OnInit {
    blog: IBlog;
    subDomain: string;
    mainDomain: string;
    customDomain: string;
    isShowEditBasicUrl: boolean;
    isShowEditCustomUrl: boolean;

    constructor(private route: ActivatedRoute, private blogService: BlogService, private alertService: JhiAlertService) {}

    ngOnInit() {
        this.isShowEditBasicUrl = false;
        this.isShowEditCustomUrl = false;
        const blogId = this.route.snapshot.queryParams['blogId'];
        this.blogService
            .getBlog(blogId)
            .subscribe(
                (res: HttpResponse<IBlog>) => this.onSuccessGetBlog(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    saveInformation() {
        const blog = { id: this.blog.id, title: this.blog.title, description: this.blog.description };
        this.blogService
            .patchBlog(blog, ['title', 'description'])
            .subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
    }

    saveBasicUrl() {
        const blog = { id: this.blog.id, url: this.subDomain + this.mainDomain };
        this.blogService
            .patchBlog(blog, ['url'])
            .subscribe(response => this.onSuccessSave(response, this.showEditBasicUrl(false)), () => this.onErrorSave());
    }

    saveCustomUrl() {
        const blog = { id: this.blog.id, customUrl: this.customDomain };
        this.blogService
            .patchBlog(blog, ['customUrl'])
            .subscribe(response => this.onSuccessSave(response, this.showEditCustomUrl(false)), () => this.onErrorSave());
    }

    showEditBasicUrl(isShow: boolean) {
        if (isShow) {
            this.subDomain = this.blog.url.substring(0, this.blog.url.indexOf('.'));
        }
        this.isShowEditBasicUrl = isShow;
    }

    showEditCustomUrl(isShow: boolean) {
        if (isShow) {
            this.customDomain = this.blog.customUrl;
        }
        this.isShowEditCustomUrl = isShow;
    }

    deleteCustomUrl() {
        const blog = { id: this.blog.id, customUrl: null };
        this.blogService
            .patchBlog(blog, ['customUrl'])
            .subscribe(response => this.onSuccessSave(response, this.showEditBasicUrl(false)), () => this.onErrorSave());
    }

    saveBlogReader() {
        const blog = { id: this.blog.id, status: this.blog.status };
        this.blogService.patchBlog(blog, ['status']).subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
    }

    private onSuccessGetBlog(data, headers) {
        this.blog = data;
        this.mainDomain = this.blog.url.substring(this.blog.url.indexOf('.'));
    }

    private onSuccessSave(response, callback?) {
        this.blog = response.body;
        if (callback) {
            callback();
        }
    }

    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    private onErrorSave() {}
}
