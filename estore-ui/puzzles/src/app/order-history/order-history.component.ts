import {Component, Inject, ViewChild} from '@angular/core';
import {Order} from "../order";
import {Puzzle} from "../puzzle";
import {OrderService} from "../order.service";
import {AccountService} from "../account.service";
import {Router} from "@angular/router";
import {PuzzlesService} from "../puzzles.service";
import {fadeInOutAnimation} from "../animations";
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheet, MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {MatListItem, MatListItemTitle, MatNavList} from "@angular/material/list";
import {MatLine} from "@angular/material/core";
import {CustomPuzzle} from "../custom_puzzle";
import {MatCard, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {NgOptimizedImage} from "@angular/common";


@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrl: './order-history.component.css',
  animations: [fadeInOutAnimation]
})


export class OrderHistoryComponent {
  protected orders?: Order[]
  constructor(private orderService: OrderService,
              private accountService: AccountService,
              private puzzlesService: PuzzlesService,
              private bottomSheet: MatBottomSheet,
              private router: Router) {

    let accountId = this.accountService.getLoggedInAccount()?.id
    if (accountId) {
      this.orderService.getOrdersByAccountId(accountId).subscribe((orders) => {
        this.orders = orders
        console.log("Orders retrieved")
      })
    }
  }

  showPurchasedPuzzleDetails(puzzle: Puzzle) {
    console.log(`Puzzle ${puzzle.id} purchased`)
    this.puzzlesService.setCurrentPuzzle(puzzle)
    this.router.navigate([`/puzzles/info`]).then(r => console.log("Navigated to puzzle"))
  }

  openCustomPuzzleDetailsBottomSheet(customPuzzle: CustomPuzzle) {
    this.bottomSheet.open(CustomPuzzleDetailsBottomSheet, {data: {"customPuzzle": customPuzzle}})

  }
}

@Component({
  selector: 'custom-puzzle-details-sheet',
  templateUrl: 'custom-puzzle-details-sheet.html',
  styleUrl: 'custom-puzzle-details-sheet.css',
  imports: [
    MatNavList,
    MatListItemTitle,
    MatLine,
    MatListItem,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    NgOptimizedImage
  ],
  standalone: true
})


export class CustomPuzzleDetailsBottomSheet {
  constructor(private bottomSheetRef: MatBottomSheetRef<CustomPuzzleDetailsBottomSheet>,
              @Inject(MAT_BOTTOM_SHEET_DATA) public data: {customPuzzle: CustomPuzzle}) {
  }
}
