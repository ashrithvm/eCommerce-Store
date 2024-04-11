import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, tap} from "rxjs";
import {Puzzle} from "./puzzle";

@Injectable({
  providedIn: 'root'
})
export class PuzzlesService {
  private url = 'http://localhost:8080/puzzles';
  private currentPuzzle?: Puzzle
  constructor(private httpClient: HttpClient) {}

  /**
   * Makes a request to the server to get all puzzles.
   * @returns an observable of the puzzles
   */
  getPuzzles() {
    return this.httpClient.get<Puzzle[]>(this.url)
  }

  /**
   * @return the most recently viewed puzzle
   */
  getCurrentPuzzle(){
    return this.currentPuzzle
  }

  /**
   * Sets the current puzzle to the given puzzle. Useful when navigating to a different page and
   * transferring the information of the current puzzle to the new page.
   * @param puzzle
   */
  setCurrentPuzzle(puzzle: Puzzle){
    this.currentPuzzle = puzzle
  }

  /**
   * Makes a request to the server to get the puzzle with the given id. Also sets the current puzzle to the
   * puzzle that is returned.
   * @param id the id of the puzzle
   * @returns an observable of the puzzle
   */
  getPuzzle(id: number) {
    return this.httpClient.get<Puzzle>(this.url + '/' + id).pipe(
      tap(puzzle => this.currentPuzzle = puzzle)
    )
  }

  /**
   * Makes a request to the server to get puzzles with names that contain the given query.
   * @param query the query to search for
   * @returns an observable of the puzzles that match the query or all puzzles if the query is an empty string
   * or null.
   */
  getPuzzleByPartialName(query: string) {
    if(query == "" || query == null)
      return this.httpClient.get<Puzzle[]>(this.url)
    return this.httpClient.get<Puzzle[]>(this.url+"/", {
      params: {
        name: query
      }
    })
  }


  /**
   * Makes a request to the server to create a new puzzle.
   * @param puzzle the puzzle to create
   * @returns an observable of the puzzle
   */
  createPuzzle(puzzle: Puzzle) {
    return this.httpClient.post(this.url, puzzle)
  }

  /**
   * Makes a request to the server to update the puzzle.
   * @param puzzle the puzzle to update
   * @returns an observable of the puzzle
   */
  updatePuzzle(puzzle: Puzzle) {
    return this.httpClient.put(this.url, puzzle)
  }

  /**
   * Makes a request to the server to delete the puzzle with the given id.
   * @param id the id of the puzzle
   * @returns an observable of the puzzle
   */
  deletePuzzle(id: number) {
    return this.httpClient.delete(this.url + '/' + id)
  }


}
