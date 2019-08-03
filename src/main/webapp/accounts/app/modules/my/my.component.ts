import { Component, OnInit, OnDestroy } from '@angular/core';
import { SIDEBAR_MENU } from '../../shared';
import { MainService } from '../../layouts';

@Component({
  selector: 'cks-my',
  templateUrl: './my.component.html'
})
export class MyComponent implements OnInit, OnDestroy {
  menuItems: any[];

  constructor(private mainService: MainService) {
    this.mainService.addContainerClass('cks-container-with-sidebar');
    this.menuItems = SIDEBAR_MENU();
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.mainService.removeContainerClass('cks-container-with-sidebar');
  }
}
