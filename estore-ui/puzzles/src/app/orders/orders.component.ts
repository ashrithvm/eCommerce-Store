import { Component } from '@angular/core';
import {OrderService} from "../order.service";
import {Order} from "../order";
import {Puzzle} from "../puzzle";
import {PuzzlesService} from "../puzzles.service";
import {Account} from "../account";
import {MatBottomSheet} from "@angular/material/bottom-sheet";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent {
  protected orders?: Order[];
  protected totalRevenue? = 0;
  protected puzzleTimesSoldMap?: Map<string, number>;
  protected bestSellingPuzzle?: string;
  protected bestCustomer?: string;
  constructor(private orderService: OrderService) {
    this.orderService.getOrders().subscribe(orders => {
      this.orders = orders;
      this.totalRevenue = orders.map(order => order.total).reduce((acc, value) => acc + value, 0);
      this.puzzleTimesSoldMap = this.getPuzzleTimesSoldMap();
      this.bestSellingPuzzle = this.getBestSellingPuzzle();
      this.bestCustomer = this.getBestCustomer();
    });
  }




  getOrderPuzzleNames(order: Order){
    return order.items.map(item => item.puzzle.name).join(", ")
  }

  getPuzzleTimesSoldMap(){
    let map = new Map<string, number>();
    this.orders?.forEach(order => {
      order.items.forEach(item => {
        let timesSold = map.get(item.puzzle.name) || 0;
        map.set(item.puzzle.name, timesSold + item.quantity);
      })
    })
    return map;
  }

  /**
   * Puzzle that has been sold the most.
   * @returns name of the best selling puzzle
   */
  getBestSellingPuzzle(): string | undefined {
    let max = 0;
    let bestSellingPuzzle;
    this.puzzleTimesSoldMap?.forEach((value, key) => {
      if(value > max){
        max = value;
        bestSellingPuzzle = key;
      }
    })
    return bestSellingPuzzle;
  }

  /**
   * Customer that has spent the most money
   * @returns Account of the best customer
   */
  getBestCustomer(): string | undefined{
    let customerTotalMap = new Map<string, number>();
    this.orders?.forEach(order => {
      let total = customerTotalMap.get(order.buyer.username) || 0;
      customerTotalMap.set(order.buyer.username, total + order.total);
    })
    let max = 0;
    let bestCustomer;
    customerTotalMap.forEach((value, key) => {
      if(value > max){
        max = value;
        bestCustomer = key;
      }
    })
    return bestCustomer;
  }
}
