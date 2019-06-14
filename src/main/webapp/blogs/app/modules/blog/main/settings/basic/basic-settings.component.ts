import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { BlogService, IBlog } from '../../../../../core';

@Component({
  selector: 'cks-blog-basic-settings',
  templateUrl: './basic-settings.component.html',
  styleUrls: ['../../main.scss', '../settings.scss', './basic-settings.scss']
})
export class BlogBasicSettingsComponent implements OnInit {
  blogId: number;
  subDomain: string;
  mainDomain: string;
  customDomain: string;
  isShowEditBasicUrl: boolean;
  isShowEditCustomUrl: boolean;
  informationForm = this.fb.group({
    title: ['', [Validators.minLength(3), Validators.maxLength(100), Validators.required]],
    description: ['']
  });
  basicUrlForm = this.fb.group({
    subDomain: ['', [Validators.maxLength(100), Validators.pattern('^([A-Za-z0-9][A-Za-z0-9-]*)*[A-Za-z0-9]$')]]
  });
  customUrlForm = this.fb.group({
    customDomain: ['', [Validators.maxLength(253), Validators.pattern('^([a-zA-Z0-9-_]+.)*[a-zA-Z0-9][a-zA-Z0-9-_]+.[a-zA-Z]{2,11}?$')]]
  });
  blogReaderForm = this.fb.group({
    status: ['', Validators.required]
  });

  constructor(
    private blogService: BlogService,
    private alertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.blogId = this.route.snapshot.queryParams['blogId'];
    this.isShowEditBasicUrl = false;
    this.isShowEditCustomUrl = false;
    this.blogService
      .getBlog(this.blogId)
      .subscribe(
        (res: HttpResponse<IBlog>) => this.onSuccessGetBlog(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  saveInformation() {
    const blog = {
      id: this.blogId,
      title: this.informationForm.get(['title']).value,
      description: this.informationForm.get(['description']).value
    };
    this.blogService
      .patchBlog(blog, ['title', 'description'])
      .subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
  }

  saveBasicUrl() {
    const blog = { id: this.blogId, url: this.basicUrlForm.get(['subDomain']).value + this.mainDomain };
    this.blogService
      .patchBlog(blog, ['url'])
      .subscribe(response => this.onSuccessSave(response, this.showEditBasicUrl(false)), () => this.onErrorSave());
  }

  saveCustomUrl() {
    const blog = { id: this.blogId, customUrl: this.customUrlForm.get(['customDomain']).value };
    this.blogService
      .patchBlog(blog, ['customUrl'])
      .subscribe(response => this.onSuccessSave(response, this.showEditCustomUrl(false)), () => this.onErrorSave());
  }

  showEditBasicUrl(isShow: boolean) {
    if (isShow) {
      this.basicUrlForm.setValue({ subDomain: this.subDomain });
    }
    this.customUrlForm.setValue({ customDomain: '' });
    this.isShowEditBasicUrl = isShow;
  }

  showEditCustomUrl(isShow: boolean) {
    if (isShow) {
      this.customUrlForm.setValue({ customDomain: this.customDomain ? this.customDomain : '' });
    }
    this.isShowEditCustomUrl = isShow;
  }

  deleteCustomUrl() {
    const blog = { id: this.blogId, customUrl: null };
    this.blogService
      .patchBlog(blog, ['customUrl'])
      .subscribe(response => this.onSuccessSave(response, this.showEditBasicUrl(false)), () => this.onErrorSave());
  }

  saveBlogReader() {
    const blog = { id: this.blogId, status: this.blogReaderForm.get(['status']).value };
    this.blogService.patchBlog(blog, ['status']).subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
  }

  private onSuccessGetBlog(data, headers) {
    this.informationForm.setValue({ title: data.title, description: data.description });
    this.subDomain = data.url.substring(0, data.url.indexOf('.'));
    this.mainDomain = data.url.substring(data.url.indexOf('.'));
    this.basicUrlForm.setValue({ subDomain: this.subDomain });
    this.customDomain = data.customUrl;
    this.customUrlForm.setValue({ customDomain: this.customDomain ? this.customDomain : '' });
    this.blogReaderForm.setValue({ status: data.status });
  }

  private onSuccessSave(response, callback?) {
    this.blogService.list(true).then(() => {
      this.eventManager.broadcast({
        name: 'blogsChange',
        content: 'Sending Blogs Change'
      });
    });
    this.subDomain = this.basicUrlForm.get(['subDomain']).value;
    this.customDomain = this.customUrlForm.get(['customDomain']).value;
    if (callback) {
      callback();
    }
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }

  private onErrorSave() {}
}
