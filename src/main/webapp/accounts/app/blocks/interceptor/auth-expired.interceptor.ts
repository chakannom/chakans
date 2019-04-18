import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { SignInService } from '../../modules/internal/sign-in/sign-in.service';

@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
    constructor(private signInService: SignInService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {},
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        if (err.status === 401) {
                            this.signInService.signOut();
                        }
                    }
                }
            )
        );
    }
}
