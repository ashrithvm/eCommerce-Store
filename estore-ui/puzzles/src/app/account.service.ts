import {afterRender, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Account} from './account';
import {catchError, Observable, tap} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private url = 'http://localhost:8080/accounts';
  private loggedInAccount?: Account
  constructor(
    private httpClient: HttpClient) {
      if (localStorage.getItem('loggedInAccount'))
        this.loggedInAccount = JSON.parse(localStorage.getItem('loggedInAccount') || '{}')
      else
        this.loggedInAccount = undefined

  }

  /**
   * Makes a request to the server to validate the account. If the account is valid, it logs in the user
   * and creates a session in local storage.
   * @param username the username
   * @returns an observable of the account, undefined if the username is not wrong
   */
  validateAccount(username: string) {
    return this.httpClient.get<Account>(`${this.url}/validate?username=${username}&token=2003`)
      .pipe(
        catchError(this.handleError("getAccount")),
        tap(account => this.login(account)
        ))
  }

  /**
   * Creates a login session in local storage.
   * @param account the account to store
   */
  private login(account: Account){
    this.loggedInAccount = account
    localStorage.setItem('loggedInAccount', JSON.stringify(account))
  }

  /**
   * Logs out the current user.
   */
  logout(){
    this.loggedInAccount = undefined
    localStorage.removeItem('loggedInAccount')
  }

  /**
   * @return the account that is currently logged in, or undefined if no account is logged in.
   */
  getLoggedInAccount(): Account | undefined {
    return this.loggedInAccount;
  }

  /**
   * @return true if any account is logged in, false otherwise.
   */
  isLoggedInUser(): boolean {
    return this.loggedInAccount !== undefined;
  }

  /**
   * @return true if and only if the logged in account is the admin account.
   */
  isLoggedInAdminUser(): boolean {
    return this.isLoggedInUser() && this.loggedInAccount?.username === 'admin';
  }

  handleError(error: any) {
    if(error.status === 404)
      console.error("Account not found");

    return (error: any):Observable<Account> => {
      console.error(error);
      return new Observable<Account>;
    }
  }

}
