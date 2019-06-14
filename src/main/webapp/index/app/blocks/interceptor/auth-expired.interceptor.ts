import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { SignOutService } from '../../core/sign-out/sign-out.service';

@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
  constructor(private signOutService: SignOutService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap(
        (event: HttpEvent<any>) => {},
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401) {
              this.signOutService.signOut();
            }
          }
        }
      )
    );
  }
}
