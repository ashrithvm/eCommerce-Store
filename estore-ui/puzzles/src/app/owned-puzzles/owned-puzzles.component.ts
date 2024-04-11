import { Component } from '@angular/core';
import {OrderService} from "../order.service";
import {AccountService} from "../account.service";
import {Order} from "../order";
import {Puzzle} from "../puzzle";
import {fadeInOutAnimation} from "../animations";
import {CustomPuzzle} from "../custom_puzzle";
import {MatBottomSheet, MatBottomSheetRef} from "@angular/material/bottom-sheet";

@Component({
  selector: 'app-owned-puzzles',
  templateUrl: './owned-puzzles.component.html',
  styleUrl: './owned-puzzles.component.css',
  animations: [fadeInOutAnimation]
})
export class OwnedPuzzlesComponent {
  protected orders?: Order[]
  protected orderPuzzles?: Puzzle[]
  protected customPuzzles?: CustomPuzzle[]
  protected totalPurchaseAmount: number = 0
  constructor(private orderService: OrderService,
              private accountService: AccountService) {
    let accountId = this.accountService.getLoggedInAccount()?.id
    if (accountId) {
      this.orderService.getOrdersByAccountId(accountId).subscribe((orders) => {
        this.orders = orders
        this.orderPuzzles = this.getPuzzlesFromOrders()
        this.customPuzzles = this.getCustomPuzzlesFromOrders()
        this.totalPurchaseAmount = this.orderPuzzles.length + this.customPuzzles.length
        console.log("Orders retrieved")
      })
    }
  }



  isEmpty() {
    return this.orderPuzzles?.length == 0 || this.orders == undefined
  }
  getPuzzlesFromOrders(): Puzzle[] {
    let puzzleNamesSet = new Set<string>()
    let puzzles: Puzzle[] = []
    for (let order of this.orders!) {
      for (let item of order.items) {
        if (!puzzleNamesSet.has(item.puzzle.name))
          puzzles.push(item.puzzle)

        puzzleNamesSet.add(item.puzzle.name)
      }

    }
    return puzzles;
  }

  getCustomPuzzlesFromOrders():CustomPuzzle[] {
    let puzzleNamesSet = new Set<string>()
    let puzzles: CustomPuzzle[] = []
    for (let order of this.orders!) {
      for (let item of order.customPuzzles) {
        if (!puzzleNamesSet.has(item.name))
          puzzles.push(item)

        puzzleNamesSet.add(item.name)
      }

    }
    return puzzles;
  }
}

