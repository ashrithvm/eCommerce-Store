import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {forkJoin, map, Observable, switchMap, tap} from "rxjs";
import {Cart} from "./cart";
import {PuzzlesService} from "./puzzles.service";
import {Puzzle} from "./puzzle";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  baseUrl = 'http://localhost:8080/carts'
  constructor(private http: HttpClient, private puzzleService: PuzzlesService) {}

  /**
   * Get the cart for the given account id.
   * @param accountId the account id
   */
  getCart(accountId: number): Observable<Cart> {
    /**
     * Custom deserialization of the http response to a Cart object so that we can
     * use the puzzle service to get the puzzle details for each puzzle in the cart.
     */
    return this.http.get<Cart>(`${this.baseUrl}/${accountId}`).pipe(
      tap(cart => { console.log(cart) })
    )
  }

/**
 * Update the quantity of a puzzle in the cart.
 * @param id
 */

  private getPuzzleDetails(id: number){
    return this.puzzleService.getPuzzle(id)
  }

  /**
   * Makes a request to the server to add a puzzle to the cart.
   * @param accountId the account id
   * @param puzzleId the puzzle id
   */
  addItemToCart(accountId: number, puzzleId: number){
    let params = new HttpParams().set('puzzleId', puzzleId.toString())
    return this.http.post<Cart>(`${this.baseUrl}/${accountId}/add`,
      null, {params: params})
  }


  /**
   * Makes a request to the server to update the cart.
   * @param cart the updated cart
   */
  updateCart(cart: Cart){
    return this.http.put<Cart>(`${this.baseUrl}/${cart.id}/update`, cart)
  }

  /**
   * Makes a request to the server to remove a puzzle from the cart.
   * @param accountId the account id
   * @param puzzleId the puzzle id
   */
  removeItemFromCart(accountId: number, puzzleId: number){

    return this.http.delete<Cart>(`${this.baseUrl}/${accountId}/remove`, {
      params: new HttpParams().set('puzzleId', puzzleId.toString())
    })
  }
}
