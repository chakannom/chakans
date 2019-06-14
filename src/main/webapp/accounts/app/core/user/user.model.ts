export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  agreements?: any;
  createdBy?: string;
  createdDate?: Date;
  modifiedBy?: string;
  modifiedDate?: Date;
  password?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public agreements?: any,
    public createdBy?: string,
    public createdDate?: Date,
    public modifiedBy?: string,
    public modifiedDate?: Date,
    public password?: string
  ) {
    this.id = id ? id : null;
    this.login = login ? login : null;
    this.firstName = firstName ? firstName : null;
    this.lastName = lastName ? lastName : null;
    this.email = email ? email : null;
    this.activated = activated ? activated : false;
    this.langKey = langKey ? langKey : null;
    this.authorities = authorities ? authorities : null;
    this.agreements = agreements ? agreements : null;
    this.createdBy = createdBy ? createdBy : null;
    this.createdDate = createdDate ? createdDate : null;
    this.modifiedBy = modifiedBy ? modifiedBy : null;
    this.modifiedDate = modifiedDate ? modifiedDate : null;
    this.password = password ? password : null;
  }
}
