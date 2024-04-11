import {Component, OnDestroy} from '@angular/core';
import {PuzzlesService} from "../puzzles.service";
import {Puzzle} from "../puzzle";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {CartService} from "../cart.service";
import {AccountService} from "../account.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {fadeInOutAnimation} from "../animations";
import {catchError} from "rxjs";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css',
  animations: [
    fadeInOutAnimation
  ]

})
export class ProductDetailsComponent implements OnDestroy{
  protected puzzle?: Puzzle
  protected state?: string = 'slideDown'
  constructor(private puzzleService: PuzzlesService,
              private cartService: CartService,
              private accountService: AccountService,
              private snackBar: MatSnackBar) {
    this.puzzle = puzzleService.getCurrentPuzzle()
  }

  isLoggedInAdminUser(){
    return this.accountService.isLoggedInAdminUser()
  }
  addToCart(puzzle: Puzzle){

    let accountId = this.accountService.getLoggedInAccount()?.id;
    if(accountId){
      this.cartService.addItemToCart(accountId, puzzle.id).pipe(
        catchError((error) => {
          this.snackBar.open("Cannot add. Quantity in cart is equal to the one in stock.", "Close", {
            duration: 2000
          });
          return error
        })).subscribe(
        (cart) => {
          this.snackBar.open("Puzzle added to cart", "Close", {
            duration: 2000,
          });
          console.log("Puzzle added to cart")
        }
      )
    }
  }

  ngOnDestroy(): void {
    this.state = 'slideUp'
  }
}
