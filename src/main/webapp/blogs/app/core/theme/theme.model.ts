export interface ITheme {
    id?: any;
    imageUrl?: string;
    name?: string;
    description?: string;
    template?: string;
    createdDate?: Date;
    modifiedDate?: Date;
}

export class Theme implements ITheme {
    constructor(
        public id?: any,
        public imageUrl?: string,
        public name?: string,
        public description?: string,
        public template?: string,
        public createdDate?: Date,
        public modifiedDate?: Date
    ) {
        this.id = id ? id : null;
        this.imageUrl = imageUrl ? imageUrl : null;
        this.name = name ? name : null;
        this.description = description ? description : null;
        this.template = template ? template : null;
        this.createdDate = createdDate ? createdDate : null;
        this.modifiedDate = modifiedDate ? modifiedDate : null;
    }
}
