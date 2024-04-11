import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {fadeInOutAnimation} from "../animations";
import {OrderService} from "../order.service";
import {CartService} from "../cart.service";
import {Order} from "../order";
import {AccountService} from "../account.service";
import {Cart} from "../cart";
import {ThemePalette} from "@angular/material/core";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {GiftService} from "../gift.service";
import {Gift} from "../gift";
import {PaymentInfo} from "../payment-info";
import {PaymentInfoService} from "../payment-info.service";
import {catchError} from "rxjs";

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrl: './checkout-page.component.css',
  animations: [fadeInOutAnimation]
})
export class CheckoutPageComponent {
  private cart?: Cart;
  private order?: Order;
  paymentInfos: PaymentInfo[] = [];
  isPaymentProcessing = false;
  showPopup = false;
  subtotal: number = 0;
  tax: number = 0;
  total: number = 0;
  checked = false;
  checked2 = false;
  isSavedPayment = false;
  isSelectedSavedPayment = false;
  selectedPaymentInfo: any = null;

  formGroup = this.formBuilder.group({
    name: new FormControl('', [Validators.required]),
    creditCard: new FormControl('', [Validators.required, Validators.minLength(16), Validators.maxLength(16)]),
    expirationDate: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(5)]),
    cvv: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
    checked: '',
    checked2: '',
    recipientEmail: new FormControl('', [Validators.required, Validators.email]),
    message: new FormControl('', [Validators.required]),
  });

  constructor(private router: Router,
              private route: ActivatedRoute,
              private orderService: OrderService,
              private cartService: CartService,
              private accountService: AccountService,
              private giftService: GiftService,
              private formBuilder: FormBuilder,
              private paymentInfoService: PaymentInfoService)
               {
    this.cartService.getCart(accountService.getLoggedInAccount()!!.id).subscribe(cart => {
      this.cart = cart;
      this.subtotal = this.cart.items.reduce((acc, item) => acc + item.puzzle.price * item.quantity, 0) + this.cart.customPuzzles.reduce((acc, customPuzzle) => acc + customPuzzle.price * customPuzzle.quantity, 0);
      this.tax = 0.07 * this.subtotal;
      this.total = this.subtotal + this.tax;
    });
    this.paymentInfoService.getAll().subscribe(paymentInfos => {
      this.paymentInfos = paymentInfos;
      if(this.paymentInfos.length > 0) {
        this.isSavedPayment = true;

      }
    });

  }


  private validateForm() {
    //clear validators for credit card info if user selects saved payment
    if(this.isSelectedSavedPayment) {
      this.formGroup.controls['name'].clearValidators();
      this.formGroup.controls['creditCard'].clearValidators();
      this.formGroup.controls['expirationDate'].clearValidators();
      this.formGroup.controls['cvv'].clearValidators();
      this.formGroup.controls.name.updateValueAndValidity();
      this.formGroup.controls.creditCard.updateValueAndValidity();
      this.formGroup.controls.expirationDate.updateValueAndValidity();
      this.formGroup.controls.cvv.updateValueAndValidity();
    }
    else {
      this.formGroup.controls['name'].setValidators([Validators.required]);
      this.formGroup.controls['creditCard'].setValidators([Validators.required, Validators.minLength(16), Validators.maxLength(16)]);
      this.formGroup.controls['expirationDate'].setValidators([Validators.required, Validators.minLength(5), Validators.maxLength(5)]);
      this.formGroup.controls['cvv'].setValidators([Validators.required, Validators.minLength(3), Validators.maxLength(3)]);
      this.formGroup.controls.name.updateValueAndValidity();
      this.formGroup.controls.creditCard.updateValueAndValidity();
      this.formGroup.controls.expirationDate.updateValueAndValidity();
      this.formGroup.controls.cvv.updateValueAndValidity();
    }
    if(!this.checked) {
      this.formGroup.controls['recipientEmail'].clearValidators();
      this.formGroup.controls['message'].clearValidators();
      this.formGroup.controls.recipientEmail.updateValueAndValidity();
      this.formGroup.controls.message.updateValueAndValidity();
    }
    else {
      this.formGroup.controls['recipientEmail'].setValidators([Validators.required, Validators.email]);
      this.formGroup.controls['message'].setValidators([Validators.required]);
      this.formGroup.controls.recipientEmail.updateValueAndValidity();
      this.formGroup.controls.message.updateValueAndValidity();
    }

    console.log(this.formGroup.controls.recipientEmail.errors);
    return this.formGroup.valid;

  }

  sendGift() {
    let gift: Gift = {
      order: this.order!,
      recipientEmail: this.formGroup.controls.recipientEmail.value!,
      message: this.formGroup.controls.message.value!
    }
    this.giftService.sendGift(gift).subscribe(() => {
      console.log("Gift sent successfully");
    });
  }

  //Changes boolean value of selectedSavedPayment to true. if true and user clicks again, it will change to false
  selectSavedPayment(paymentInfo: any) {
    if (this.selectedPaymentInfo === paymentInfo) {
      // If the clicked card is already selected, unselect it
      this.selectedPaymentInfo = null;
      this.isSelectedSavedPayment = false;
    } else {
      // Otherwise, select the clicked card
      this.selectedPaymentInfo = paymentInfo;
      this.isSelectedSavedPayment = true;
    }
  }

  //Takes payment details from forms and saves it to the database using the paymentInfoService
  onSavedPayment() {
let paymentInfo: PaymentInfo = {
      id: 0,
      accountId: this.accountService.getLoggedInAccount()!.id,
      name: this.formGroup.controls.name.value!,
      cardNumber: this.formGroup.controls.creditCard.value!,
      expirationDate: this.formGroup.controls.expirationDate.value!,
      securityCode: parseInt(this.formGroup.controls.cvv.value!)
    }
    this.paymentInfoService.create(paymentInfo).subscribe(() => {
      console.log("Payment info saved successfully");
    });
  }

  onSubmit() {
    if(!this.validateForm()) {
      alert("Please fill out all required fields.")
      return;
    }

    if(this.cart){
       this.order = new Order(0, this.accountService.getLoggedInAccount()!, this.cart.items, this.cart.customPuzzles, this.total);
      console.log(this.order);
    }
    this.orderService.createOrder(this.order!).pipe(
      catchError((err) => {
        console.log(err);
        alert("An error occurred while creating the order. Please try again.")
        location.reload();
        return [];
      })
    )
      .subscribe((order) => {
      this.order = order;
      if(this.checked)
        this.sendGift();
      if(this.checked2)
        this.onSavedPayment();
      console.log("Order created successfully");
    });


    this.isPaymentProcessing = true;
    this.showPopup = true;

    setTimeout(() => {
      this.isPaymentProcessing = false; // Hide the buffering icon
      setTimeout(() => {
        this.showPopup = false; // Hide the popup after the tick mark has been shown for 1 second
        this.router.navigate(['/order-history']).then(r => console.log("Navigated to order history"));
      }, 1000); // Wait for 1 second before hiding the popup
    }, 1000); // 1 second delay for the buffering icon
  }


}
