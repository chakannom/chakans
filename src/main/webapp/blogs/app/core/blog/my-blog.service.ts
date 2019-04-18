import { Injectable } from '@angular/core';

import { BlogService, IBlog } from '../';

@Injectable({ providedIn: 'root' })
export class MyBlog {
    private blogs: IBlog[];

    constructor(private blogService: BlogService) {}

    push(blog: IBlog) {
        this.blogs.push(blog);
    }

    list(force?: boolean): Promise<IBlog[]> {
        if (force === true) {
            this.blogs = undefined;
        }

        // check and see if we have retrieved the userProfile data from the server.
        // if we have, reuse it by immediately resolving
        if (this.blogs) {
            return Promise.resolve(this.blogs);
        }

        // retrieve the userProfile data from the server, update the userProfile object, and then resolve.
        return this.blogService
            .getBlogs({ unpaged: 'true' })
            .toPromise()
            .then(response => {
                const blogs = response.body;
                if (blogs) {
                    this.blogs = blogs;
                } else {
                    this.blogs = null;
                }
                return this.blogs;
            })
            .catch(err => {
                this.blogs = null;
                return null;
            });
    }
}
