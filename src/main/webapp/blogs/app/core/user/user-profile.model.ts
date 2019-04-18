export interface IUserProfile {
    id?: any;
    nickname?: string;
    email?: string;
    imageUrl?: string;
    openedProfile?: boolean;
    openedEmail?: boolean;
    createdDate?: Date;
    modifiedDate?: Date;
}

export class UserProfile implements IUserProfile {
    constructor(
        public id?: any,
        public nickname?: string,
        public email?: string,
        public imageUrl?: string,
        public openedProfile?: boolean,
        public openedEmail?: boolean,
        public createdDate?: Date,
        public modifiedDate?: Date
    ) {
        this.id = id ? id : null;
        this.nickname = nickname ? nickname : null;
        this.email = email ? email : null;
        this.imageUrl = imageUrl ? imageUrl : '';
        this.openedProfile = openedProfile ? openedProfile : false;
        this.openedEmail = openedEmail ? openedEmail : false;
        this.createdDate = createdDate ? createdDate : null;
        this.modifiedDate = modifiedDate ? modifiedDate : null;
    }
}
