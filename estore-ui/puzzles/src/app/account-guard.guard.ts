import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {inject} from "@angular/core";
import {AccountService} from "./account.service";

export const loginGuard = () => {
  const accountService = inject(AccountService)
  const router = inject(Router)
  if(!accountService.isLoggedInUser()) {
    return true;
  }
  return router.parseUrl('/puzzles')

};

export const adminGuard = () => {
  const accountService = inject(AccountService)
  const router = inject(Router)
  if(accountService.isLoggedInAdminUser()) {
    return true;
  }
  alert("You are not authorized to view this page.")
  return router.parseUrl('/login')
}

export const productGuard = (guard: CanActivateFn) => {
  const accountService = inject(AccountService)
  const router = inject(Router)
  if(!accountService.isLoggedInAdminUser()){
    return true
  }
  return router.parseUrl('/admin')
}

export const cartGuard = () => {
  const accountService = inject(AccountService)
  const router = inject(Router)
  if(accountService.isLoggedInUser()) {
    return true;
  }
  return router.parseUrl('/login')
}
