export interface IPostTag {
  name?: string;
  postCount?: number;
}

export class PostTag implements IPostTag {
  constructor(public name?: string, public postCount?: number) {
    this.name = name ? name : null;
    this.postCount = postCount ? postCount : 0;
  }
}
