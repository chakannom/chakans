import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { BlogService } from '../';

@Injectable({ providedIn: 'root' })
export class BlogRouteAccessService implements CanActivate {
  constructor(private router: Router, private blogService: BlogService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    // This could happen on a page refresh.
    return this.accessible(parseInt(route.queryParams['blogId'], 10), route.fragment, route.data.mainComponents);
  }

  accessible(blogId: number, fragment: string, components: any): Promise<boolean> {
    return this.blogService.list().then(blogs => {
      const componentType = blogs.length > 0 ? 'existBlog' : 'emptyBlog';
      const componentName = Object.keys(components[componentType]).find(compName => fragment && compName === fragment.split('/')[0]);
      if ((blogs.length > 0 && !blogId) || !componentName) {
        const defaultBlogId = blogs.length > 0 ? blogs[0].id : null;
        const defaultFragment = blogs.length > 0 ? 'posts/published' : 'welcome';
        this.router.navigate(['/blog.cb'], { queryParams: { blogId: defaultBlogId }, fragment: defaultFragment });
        return false;
      }
      return true;
    });
  }
}
