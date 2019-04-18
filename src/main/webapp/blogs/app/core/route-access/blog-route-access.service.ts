import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { MyBlog } from '../blog/my-blog.service';

@Injectable({ providedIn: 'root' })
export class BlogRouteAccessService implements CanActivate {
    constructor(private router: Router, private myBlog: MyBlog) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        // This could happen on a page refresh.
        return this.checkUrl(parseInt(route.queryParams['blogId'], 10), route.fragment);
    }

    checkUrl(blogId: number, fragment: string): Promise<boolean> {
        const myBlog = this.myBlog;
        return Promise.resolve(
            myBlog.list().then(blogs => {
                if (blogs.length > 0) {
                    if (!isNaN(blogId) && blogs.findIndex(blog => blog.id === blogId) > -1) {
                        return true;
                    }
                    this.router.navigate(['/blog.cb'], { queryParams: { 'blogId': blogs[0].id }, 'fragment': fragment });
                    return false;
                } else {
                    if (isNaN(blogId)) {
                        return true;
                    }
                    this.router.navigate(['/blog.cb'], { 'fragment': fragment });
                    return false;
                }
            })
        );
    }
}
