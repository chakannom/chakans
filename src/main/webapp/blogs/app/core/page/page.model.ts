export interface IPage {
    id?: any;
    title?: string;
    content?: string;
    viewedCount?: number;
    commentCount?: number;
    permitComment?: boolean;
    status?: string;
    createdDate?: Date;
    modifiedDate?: Date;
}

export class Page implements IPage {
    constructor(
        public id?: any,
        public title?: string,
        public content?: string,
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
        this.viewedCount = viewedCount ? viewedCount : 0;
        this.commentCount = commentCount ? commentCount : 0;
        this.permitComment = permitComment ? permitComment : false;
        this.status = status ? status : null;
        this.createdDate = createdDate ? createdDate : null;
        this.modifiedDate = modifiedDate ? modifiedDate : null;
    }
}
