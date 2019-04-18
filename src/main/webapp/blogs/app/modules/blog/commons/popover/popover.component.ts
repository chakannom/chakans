import { Component, Input, ViewChild, HostListener, OnInit, ElementRef } from '@angular/core';
import { NgbPopover, NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'cks-blogs-blog-popover',
    templateUrl: './popover.component.html',
    styleUrls: ['popover.css'],
    providers: [NgbPopoverConfig]
})
export class PopoverComponent implements OnInit {
    @Input()
    type: string;
    @Input()
    cksPopover: any;
    @Input()
    cksValue: any;
    @ViewChild('p')
    p: NgbPopover;

    constructor(private config: NgbPopoverConfig, private elementRef: ElementRef) {
        config.placement = ['bottom-right', 'left', 'top-right', 'auto'];
        config.triggers = 'manual';
        config.container = 'body';
    }

    ngOnInit() {}

    @HostListener('document:click', ['$event'])
    outsideClick(event) {
        if (!this.elementRef.nativeElement.contains(event.target)) {
            this.p.close();
        }
    }

    @HostListener('window:resize', ['$event'])
    onResize(event) {
        if (this.p.isOpen()) {
            this.p.close();
        }
    }

    changePopoverState() {
        if (this.p.isOpen()) {
            this.p.close();
        } else {
            this.p.open(this.cksValue);
        }
    }
}
