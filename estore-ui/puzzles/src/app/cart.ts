import {PuzzleCartItem} from "./puzzle-cart-item";
import {CustomPuzzle} from "./custom_puzzle";

export interface Cart {
  id: number;
  items: PuzzleCartItem[];
  customPuzzles: CustomPuzzle[];
}
