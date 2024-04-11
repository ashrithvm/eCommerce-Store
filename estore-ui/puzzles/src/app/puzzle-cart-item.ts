import {Puzzle} from "./puzzle";
import {Observable} from "rxjs";

export interface PuzzleCartItem {
  puzzle: Puzzle;
  quantity: number;
}
