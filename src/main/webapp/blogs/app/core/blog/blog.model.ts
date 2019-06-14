export interface IBlog {
  id?: any;
  title?: string;
  description?: string;
  url?: string;
  customUrl?: string;
  langKey?: string;
  status?: string;
  design?: any;
  createdDate?: Date;
  modifiedDate?: Date;
}

export class Blog implements IBlog {
  constructor(
    public id?: any,
    public title?: string,
    public description?: string,
    public url?: string,
    public customUrl?: string,
    public langKey?: string,
    public status?: string,
    public design?: any,
    public createdDate?: Date,
    public modifiedDate?: Date
  ) {
    this.id = id ? id : null;
    this.title = title ? title : null;
    this.description = description ? description : null;
    this.url = url ? url : null;
    this.customUrl = customUrl ? customUrl : null;
    this.langKey = langKey ? langKey : null;
    this.status = status ? status : null;
    this.design = design ? design : null;
    this.createdDate = createdDate ? createdDate : null;
    this.modifiedDate = modifiedDate ? modifiedDate : null;
  }
}
