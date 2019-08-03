import { Component, OnInit, OnDestroy, ElementRef, Renderer2 } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { NgbDateStruct, NgbCalendar, NgbTimeStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiAlertService } from 'ng-jhipster';
import { CksSidebarService } from 'ng-chakans';
import { SERVER_API_URL, IMGPROXY_URL, IMGPROXY_KEY, IMGPROXY_SALT } from '../../../../app.constants';
import { parseFragment } from '../../../../shared';
import { EditorService } from './editor.service';
import { BlogEditorRestoreModalComponent } from './editor-restore-modal.component';

@Component({
  selector: 'cks-blog-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['../main.scss', './editor.scss']
})
export class BlogEditorComponent implements OnInit, OnDestroy {
  private isClickSuggestionTag: boolean;
  tinymce: any;
  blogId: number;
  editorType: string;
  editorId: number;
  prevMenu: string;
  rowNum: number;
  content: any;
  tags: any[];
  openingDate: NgbDateStruct;
  openingTime: NgbTimeStruct;

  constructor(
    private editorService: EditorService,
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private router: Router,
    private route: ActivatedRoute,
    private alertService: JhiAlertService,
    private sidebarService: CksSidebarService,
    private calendar: NgbCalendar,
    private modalService: NgbModal,
    private localStorage: LocalStorageService,
    private sessionStorage: SessionStorageService
  ) {
    this.sidebarService.setSidebarViewed(false);
    this.tinymce = {
      baseUrl: 'https://fs.chakans.com/cdn/ajax/libs/tinymce/5.0.0/',
      init: {
        menubar: false,
        resize: false,
        branding: false,
        statusbar: false,
        height: 'calc(100vh - 13.6em)',
        min_height: '100',
        forced_root_block: 'p',
        object_resizing: 'table',
        // skin_url: 'https://fs.chakans.com/cdn/cks/blogs/1.0.0/tinymce/lightgray',
        // content_css: 'https://fs.chakans.com/cdn/cks/blogs/1.0.0/css/content.css?' + new Date().getTime(),
        // language: 'ko_KR',
        plugins: [
          'lists advlist paste preview searchreplace code',
          'table insertdatetime link charmap',
          'cks_image cks_imagetools',
          'autolink'
          // 'media',
        ],
        toolbar:
          'undo redo | fontselect fontsizeselect formatselect ' +
          '| bold italic underline strikethrough forecolor backcolor ' +
          '| align bullist numlist removeformat | table cks_image code',
        contextmenu: 'link inserttable | cell row column deletetable',
        //                mobile: {
        //                    theme: 'mobile',
        //                    plugins: [ 'autosave', 'lists', 'autolink' ]
        //                },
        font_formats:
          'Arial=arial,helvetica,sans-serif;Courier New=courier new,courier;' +
          'Georgia=georgia,palatino;Helvetica=helvetica;' +
          'Verdana=verdana,geneva;',
        fontsize_formats: '8pt 10pt 12pt 14pt 18pt 24pt 36pt',
        block_formats: 'Heading 1=h1;Heading 2=h2;Heading 3=h3;Paragraph=p;Preformatted=pre;',
        cks_image_params: {
          default_token: this.getToken(),
          presigned_put_url: 'http://localhost:8080/' + 'apis/blog/v1/storages/blogs/1/presigned-put-url',
          images_url: 'http://localhost:8080/' + 'apis/blog/v1/storages/blogs/1',
          imgproxy_url: `${IMGPROXY_URL}`,
          imgproxy_key: `${IMGPROXY_KEY}`,
          imgproxy_salt: `${IMGPROXY_SALT}`
        }
      }
    };
    this.isClickSuggestionTag = false;
    this.content = { status: 'DRAFT', permitComment: false };
    const currentDateTime = new Date();
    this.openingDate = {
      year: currentDateTime.getFullYear(),
      month: currentDateTime.getMonth() + 1,
      day: currentDateTime.getDate()
    };
    this.openingTime = {
      hour: currentDateTime.getHours(),
      minute: currentDateTime.getMinutes(),
      second: 0
    };
  }

  ngOnInit() {
    this.blogId = this.route.snapshot.queryParams['blogId'];
    const fragmentParams = parseFragment(this.route.snapshot.fragment);
    this.editorType = fragmentParams['editor/type'] === 'page' ? 'page' : 'post';
    this.editorId = fragmentParams[this.editorType + 'Id'];
    if (this.editorId !== undefined) {
      this.editorService
        .getContent(this.editorType, this.blogId, this.editorId)
        .subscribe(
          (res: HttpResponse<any>) => this.onSuccessGetContent(res.body, res.headers),
          (res: HttpResponse<any>) => this.onError(res.body)
        );
      this.prevMenu = fragmentParams['prevMenu'];
      this.rowNum = fragmentParams['rowNum'];
    }
    this.editorService
      .getTags(this.editorType, this.blogId)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessGetTags(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  ngOnDestroy() {
    this.sidebarService.setSidebarViewed(true);
  }

  expandSuggestionTags(event) {
    this.renderer.addClass(this.elementRef.nativeElement.querySelector('.suggestion-tags'), 'expanded');
  }

  collapseSuggestionTags(event) {
    setTimeout(() => {
      if (!this.isClickSuggestionTag) {
        this.renderer.removeClass(this.elementRef.nativeElement.querySelector('.suggestion-tags'), 'expanded');
      }
      this.isClickSuggestionTag = false;
    }, 300);
  }

  addTag(event) {
    this.isClickSuggestionTag = true;
    setTimeout(() => this.renderer.selectRootElement('.tag-inputbox').focus(), 0);
  }

  update() {
    this.editorService
      .updateContent(this.editorType, this.blogId, this.content)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessUpdateContent(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  moveToDraft() {
    this.content.status = 'DRAFT';
    this.editorService
      .updateContent(this.editorType, this.blogId, this.content)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessMoveToDraft(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  publish() {
    this.content.status = 'PUBLISHED';
    this.editorService
      .updateContent(this.editorType, this.blogId, this.content)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessPublish(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  save() {
    this.editorService
      .updateContent(this.editorType, this.blogId, this.content)
      .subscribe(
        (res: HttpResponse<any>) => this.onSuccessSaveContent(res.body, res.headers),
        (res: HttpResponse<any>) => this.onError(res.body)
      );
  }

  preview() {}

  close() {
    this.router.navigate(['/blog.cb'], {
      queryParams: { blogId: this.blogId },
      fragment: this.prevMenu + ';rowNum=' + this.rowNum
    });
  }

  private getToken() {
    return this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
  }

  private onSuccessGetContent(data, headers) {
    if (data.status === 'TRASHED') {
      this.showRestoreModal(data.blogId, data.id);
    }
    this.content = data;
  }

  private onSuccessGetTags(data, headers) {
    this.tags = data;
  }

  private onSuccessUpdateContent(data, headers) {
    this.router.navigate(['/blog.cb'], {
      queryParams: { blogId: this.blogId },
      fragment: this.editorType + 's/published;rowNum=' + this.rowNum
    });
  }

  private onSuccessMoveToDraft(data, headers) {
    // this.content = data;
  }

  private onSuccessPublish(data, headers) {
    this.router.navigate(['/blog.cb'], {
      queryParams: { blogId: this.blogId },
      fragment: this.editorType + 's/published;rowNum=' + this.rowNum
    });
  }

  private onSuccessSaveContent(data, headers) {
    // this.content = data;
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }

  private showRestoreModal(blogId: number, editorId: number) {
    const modalRef = this.modalService.open(BlogEditorRestoreModalComponent, {
      backdrop: 'static',
      backdropClass: 'not-fade',
      centered: true,
      keyboard: false
    });
    modalRef.componentInstance.currentBlogId = blogId;
    modalRef.componentInstance.currentEditorId = editorId;
    modalRef.result.then(
      result => {},
      reason => {
        if (reason === 'notsave') {
          this.close();
        } else if (reason === 'restore') {
          this.moveToDraft();
        }
      }
    );
  }
}
