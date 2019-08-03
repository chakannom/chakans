import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class MainService {
  containerClasses: string[];

  constructor() {
    this.containerClasses = [];
  }

  getContainerClasses() {
    return this.containerClasses;
  }

  addContainerClass(classname: string) {
    this.containerClasses.push(classname);
  }

  removeContainerClass(classname: string) {
    const pos = this.containerClasses.indexOf(classname);
    if (pos > -1) {
      this.containerClasses.splice(pos, 1);
    }
  }
}
