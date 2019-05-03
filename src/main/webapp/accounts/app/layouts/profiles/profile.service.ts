import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from '../../app.constants';
import { ProfileInfo } from './profile-info.model';

@Injectable({ providedIn: 'root' })
export class ProfileService {
    private infoUrl = SERVER_API_URL + 'management/info';
    private profileInfo: Promise<ProfileInfo>;

    constructor(private http: HttpClient) {}

    getProfileInfo(): Promise<ProfileInfo> {
        if (!this.profileInfo) {
            this.profileInfo = this.http
                .get<ProfileInfo>(this.infoUrl, { observe: 'response' })
                .pipe(
                    map((res: HttpResponse<ProfileInfo>) => {
                        const data = res.body;
                        const pi = new ProfileInfo();
                        pi.activeProfiles = data['activeProfiles'];
                        const displayRibbonOnProfiles = data['display-ribbon-on-profiles'].split(',');
                        if (pi.activeProfiles) {
                            const ribbonProfiles = displayRibbonOnProfiles.filter(profile => pi.activeProfiles.includes(profile));
                            if (ribbonProfiles.length !== 0) {
                                pi.ribbonEnv = ribbonProfiles[0];
                            }
                        }
                        return pi;
                    })
                )
                .toPromise();
        }
        return this.profileInfo;
    }
}