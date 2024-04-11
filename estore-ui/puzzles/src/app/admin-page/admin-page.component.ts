import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {AccountService} from "../account.service";
import {Router} from "@angular/router";
import {PuzzlesService} from "../puzzles.service";
import {Puzzle} from "../puzzle";
import {fadeInOutAnimation} from "../animations";

@Component({
  selector: 'app-admin',
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css',
  animations: [fadeInOutAnimation]
})
export class AdminPageComponent {
  puzzles?: Puzzle[]
  allPuzzles?: Puzzle[]
  lowStockPuzzles?: Puzzle[]
  filterState = false
  inventorySize = 0
  noStock = false
  constructor(
    private puzzleService: PuzzlesService,
    private accountService: AccountService,
    private router: Router) {
    this.puzzleService.getPuzzles()
      .subscribe(puzzles => {
        this.puzzles = puzzles
        this.allPuzzles = puzzles
        this.inventorySize = this.allPuzzles.length
        this.lowStockPuzzles = puzzles.map(puzzle => puzzle).filter(puzzle => puzzle.quantity < 5)

      })

  }


  /**
   * Check local storage to see if the user is logged in.
   * @return true if the user is logged in, false otherwise.
   */
  isLoggedIn(){
    return this.accountService.isLoggedInAdminUser()
  }

  setCurrentPuzzle(puzzle: Puzzle){
    this.puzzleService.setCurrentPuzzle(puzzle)
  }

  /**
   * Log the user out and redirect thxem to the login page.
   */
  logout() {
    this.accountService.logout()
    this.router.navigate(['/login']).then(r => console.log(r))
  }

  /**
   * Asks for confirmation and then deletes the puzzle with the given id. Nothing happens if the user
   * cancels the confirmation.
   * @param id the id of the puzzle to delete
   */
  protected deletePuzzle(id: number){
    if(!confirm("Are you sure you want to delete this puzzle?"))
      return
    this.puzzleService.deletePuzzle(id).subscribe(
      {
        next: () => {
          this.puzzleService.getPuzzles()
            .subscribe(puzzles => this.puzzles = puzzles)
        },
        error: (err) => {
          console.log(err)
        }
      }
    )
  }

  filterPuzzles(){
    if(this.filterState)
      this.puzzles = this.lowStockPuzzles
    else
      this.puzzles = this.allPuzzles
  }
}
