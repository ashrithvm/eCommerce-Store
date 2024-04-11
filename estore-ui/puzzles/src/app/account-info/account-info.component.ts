import { Component } from '@angular/core';
import {AccountService} from "../account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
  styleUrl: './account-info.component.css'
})
export class AccountInfoComponent {
  protected loggedInUsername = this.accountService.getLoggedInAccount()?.username
  protected localStorage?: Storage
  constructor(private accountService: AccountService, private router: Router) {
    try{
      this.localStorage = localStorage
    }
    catch(e){
      console.log("Local storage is not available")
      this.localStorage = undefined
    }
  }
  isUserLoggedIn() {
    return this.accountService.isLoggedInUser()
  }
  isUserAdmin() {
    return this.isUserLoggedIn() && this.accountService.isLoggedInAdminUser()
  }

  logout() {
    this.accountService.logout()
    this.router.navigate(['login']).then(r => console.log("Navigated to login"))
  }
}
