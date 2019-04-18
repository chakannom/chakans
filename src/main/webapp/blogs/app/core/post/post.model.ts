export interface IPost {
    id?: any;
    title?: string;
    content?: string;
    postName?: string;
    openedDate?: Date;
    viewedCount?: number;
    commentCount?: number;
    permitComment?: boolean;
    status?: string;
    createdDate?: Date;
    modifiedDate?: Date;
}

export class Post implements IPost {
    constructor(
        public id?: any,
        public title?: string,
        public content?: string,
        public postName?: string,
        public openedDate?: Date,
        public viewedCount?: number,
        public commentCount?: number,
        public permitComment?: boolean,
        public status?: string,
        public createdDate?: Date,
        public modifiedDate?: Date
    ) {
        this.id = id ? id : null;
        this.title = title ? title : null;
        this.content = content ? content : null;
        this.postName = postName ? postName : null;
        this.openedDate = openedDate ? openedDate : null;
        this.viewedCount = viewedCount ? viewedCount : 0;
        this.commentCount = commentCount ? commentCount : 0;
        this.permitComment = permitComment ? permitComment : false;
        this.status = status ? status : null;
        this.createdDate = createdDate ? createdDate : null;
        this.modifiedDate = modifiedDate ? modifiedDate : null;
    }
}
