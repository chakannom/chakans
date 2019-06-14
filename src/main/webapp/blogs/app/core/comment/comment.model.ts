export interface IComment {
  id?: any;
  content?: string;
  parent?: any;
  page?: any;
  post?: any;
  author?: any;
  createdDate?: Date;
  modifiedDate?: Date;
}

export class Comment implements IComment {
  constructor(
    public id?: any,
    public content?: string,
    public parent?: any,
    public page?: any,
    public post?: any,
    public author?: any,
    public createdDate?: Date,
    public modifiedDate?: Date
  ) {
    this.id = id ? id : null;
    this.content = content ? content : null;
    this.parent = parent ? parent : null;
    this.page = page ? page : null;
    this.post = post ? post : null;
    this.author = author ? author : null;
    this.createdDate = createdDate ? createdDate : null;
    this.modifiedDate = modifiedDate ? modifiedDate : null;
  }
}
