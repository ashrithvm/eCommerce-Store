import {Component, Input, OnInit, Output} from '@angular/core';
import {Account} from "../account";
import {AccountService} from "../account.service";
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";
import {Router} from "@angular/router";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent{
  matcher = new MyErrorStateMatcher();
  account?: Account
  accountNotFound = false
  message?: string
  loginForm = this.formBuilder.group({
      username: ['', Validators.required]
    });
  loginFormControl = new FormControl('', [Validators.required]);
  constructor(
    private accountService: AccountService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}
  onSubmit(): void {
    if(this.loginForm.value.username) {
      this.account = undefined
      this.accountNotFound = true
      this.accountService.validateAccount(this.loginForm.value.username)
        .subscribe(account => {
            this.account = account
            this.redirectIfSuccessful(account)
          })
    }
    else {
      alert("Username cannot be empty.")
      this.message = ""
    }
  }

  isLoggedIn(): boolean {
    return this.accountService.isLoggedInUser();
  }

  private redirectIfSuccessful(account: Account): void {
    if(account.username == "admin")
      this.router.navigate(['/admin']).then(r => console.log(r))
    else
      this.router.navigate(['/puzzles']).then(r => console.log(r))
    location.reload()
  }
}
