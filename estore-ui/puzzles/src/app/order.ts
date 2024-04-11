import {Account} from "./account";
import {Puzzle} from "./puzzle";
import {PuzzleCartItem} from "./puzzle-cart-item";
import {CustomPuzzle} from "./custom_puzzle";

export class Order{
  id: number;
  buyer: Account;
  items: PuzzleCartItem[];
  customPuzzles: CustomPuzzle[];
  total: number;
  constructor(id:number, buyer: Account, items: PuzzleCartItem[], customPuzzles: CustomPuzzle[], total:number){
    this.id = id;
    this.buyer = buyer;
    this.items = items;
    this.customPuzzles = customPuzzles;
    this.total = total;
  }


  hashCode(): number {
    return this.id;
  }
}
