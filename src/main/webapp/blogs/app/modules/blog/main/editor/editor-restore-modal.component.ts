import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'cks-editor-restore-modal',
    templateUrl: './editor-restore-modal.component.html'
})
export class BlogEditorRestoreModalComponent implements OnInit {
    constructor(public activeModal: NgbActiveModal) {}

    ngOnInit() {}
}
