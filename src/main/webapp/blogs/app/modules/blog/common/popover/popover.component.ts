import { Component, Input, ViewChild, HostListener, OnInit, ElementRef } from '@angular/core';
import { NgbPopover, NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'cks-blog-popover',
  templateUrl: './popover.component.html',
  styleUrls: ['popover.scss'],
  providers: [NgbPopoverConfig]
})
export class PopoverComponent implements OnInit {
  @ViewChild('popover', { read: NgbPopover, static: true })
  private popover: NgbPopover;
  @Input()
  type: string;
  @Input()
  cksPopover: any;
  @Input()
  cksValue: any;

  constructor(private config: NgbPopoverConfig, private elementRef: ElementRef) {
    config.placement = ['bottom-right', 'left', 'top-right', 'auto'];
    config.triggers = 'manual';
    config.container = 'body';
  }

  ngOnInit() {}

  @HostListener('document:click', ['$event'])
  outsideClick(event) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.popover.close();
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    if (this.popover.isOpen()) {
      this.popover.close();
    }
  }

  changePopoverState() {
    if (this.popover.isOpen()) {
      this.popover.close();
    } else {
      this.popover.open(this.cksValue);
    }
  }
}
