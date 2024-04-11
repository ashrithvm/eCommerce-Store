import {Component} from '@angular/core';
import {CartService} from "../cart.service";
import {AccountService} from "../account.service";
import {Cart} from "../cart";
import {PuzzleCartItem} from "../puzzle-cart-item";
import {fadeInOutAnimation} from "../animations";
import {Router} from "@angular/router";
import {CustomPuzzleService} from "../custom-puzzle.service";

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.css',
  animations: [fadeInOutAnimation]
})
export class CartPageComponent {
  protected cart?: Cart
  protected cartItems?: PuzzleCartItem[]
  protected total: number = 0
  protected isEmpty: boolean = true

  constructor(private router: Router,
              private cartService: CartService,
              private accountService: AccountService,
              private customPuzzleService: CustomPuzzleService) {
    this.getCart()
  }

  onQuantityChange() {
    this.cartService.updateCart(this.cart!).subscribe(
      (cart) => {
        console.log("Cart updated")
        this.total = this.cartItems!.reduce((acc, item) => acc + item.puzzle.price * item.quantity, 0) + cart.customPuzzles.reduce((acc, item) => acc + item.price * item.quantity, 0)
      }
    )
  }

  getCart() {
    let accountId = this.accountService.getLoggedInAccount()?.id
    if (accountId) {
      this.cartService.getCart(accountId).subscribe((cart) => {
        this.cart = cart
        this.cartItems = cart.items
        this.total = this.cartItems.reduce((acc, item) => acc + item.puzzle.price * item.quantity, 0) + cart.customPuzzles.reduce((acc, item) => acc + item.price * item.quantity, 0)
        this.isEmpty = (this.cartItems.length == 0 && this.cart.customPuzzles.length == 0) || this.cart == undefined
        console.log("Cart retrieved")
      })
    }
  }

  removeItem(puzzleId: number) {
    if (!confirm("Are you sure you want to remove this item from your cart?"))
      return
    this.cartService.removeItemFromCart(this.cart!.id, puzzleId).subscribe(
      () => {
        this.getCart()
        if (this.cartItems?.length == 1)
          this.cartItems = undefined
        console.log("Item removed from cart")
      }
    )

  }

  removeCustomPuzzle(customPuzzleId: number) {
  if (!confirm("Are you sure you want to remove this item from your cart? " +
    "This action will remove the custom puzzle entirely."))
      return
    this.customPuzzleService.delete(customPuzzleId).subscribe(
      () => {
        this.getCart()
        console.log("Custom puzzle removed from cart")
      }
    )
  }
  onCheckout() {
    if (this.isEmpty) {
      alert("Your cart is empty!")
      return
    }
    this.router.navigate(['/checkout']).then(r => console.log("Navigated to checkout"));
  }
}
