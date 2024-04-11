import {Component, ViewChild} from '@angular/core';
import {AccountService} from "../account.service";

@Component({
  selector: 'app-navigation-list',
  templateUrl: './navigation-list.component.html',
  styleUrl: './navigation-list.component.css'
})
export class NavigationListComponent {

  private localStorage?: Storage

  constructor(private accountService: AccountService){
    try{
      this.localStorage = localStorage
    }
    catch(e){
      console.log("Local storage is not available")
      this.localStorage = undefined
    }
  }

  isLoggedInAdminUser(){
    return this.accountService.isLoggedInAdminUser()
  }


}
