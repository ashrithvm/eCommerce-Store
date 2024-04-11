import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {PuzzlesService} from "../puzzles.service";
import {Puzzle} from "../puzzle";
import {AccountService} from "../account.service";
import {Router} from "@angular/router";
import {CartService} from "../cart.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {catchError, map, noop, Observable, Observer, of, switchMap, tap} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {fadeInOutAnimation} from "../animations";


@Component({
  selector: 'app-puzzle',
  templateUrl: './puzzle-page.component.html',
  styleUrl: './puzzle-page.component.css',
  animations: [fadeInOutAnimation]
})
export class PuzzlePageComponent implements OnInit, OnDestroy{
  puzzles?: Puzzle[]
  state?: string

  // Variables for the reactive search bar state management
  protected search?: string
  protected suggestions$?: Observable<string[]>
  protected error?: string

  username = this.accountService.getLoggedInAccount()?.username

  // Variables for the modal state management. The modal is used for giving a prompt to the user.
  protected modalRef?: BsModalRef
  protected message?: string
  constructor(private puzzleService: PuzzlesService,
              private accountService: AccountService,
              private cartService: CartService,
              private router: Router,
              private modalService: BsModalService,
              private snackBar: MatSnackBar) {
    this.puzzleService.getPuzzles()
      .subscribe(puzzles => this.puzzles = puzzles)

    this.state = 'slideDown'
  }

  ngOnDestroy(): void {
    this.toggleAnimation()
    }

  /**
   * Initialize the suggestions observable with the search bar input. The suggestions observable
   * is used to provide suggestions to the user as they type in the search bar. The suggestions are
   * returned from the server based on the query that the user has typed in so far.
   */
  ngOnInit() {
    this.suggestions$ = new Observable((observer: Observer<string | undefined>) => {
      observer.next(this.search);
    }).pipe(
      switchMap((query: string | undefined) => {
        if(query){
          return this.puzzleService.getPuzzleByPartialName(query).pipe(
            map(puzzles => puzzles.map(puzzle => puzzle.name))
          )
        }
        return of([])
      })
    )
  }

  /**
   * Open the modal with the given template.
   * @param template the template to use for the modal
   * @return the modal reference
   */
  openModal(template: TemplateRef<void>){
    this.modalRef = this.modalService.show(template, {class: 'modal-sm'})
  }

  /**
   * Confirm the modal and hide it.
   */
  confirm(): void {
    this.message = 'Confirmed!';
    this.modalRef?.hide()
  }

/**
   * Decline the modal and hide it.
   */
  decline(): void {
    this.message = 'Declined!';
    this.modalRef?.hide()
    this.router.navigate([`cart`]).then(r => console.log(r))
  }

  /**
   * Finds puzzles that match the search query and sets the puzzles to the found puzzles.
   */
  searchQuery(){
    this.puzzleService.getPuzzleByPartialName(this.search!).subscribe(
        (puzzles) => this.puzzles = puzzles
      )


  }
  toggleAnimation() {
    this.state = this.state === 'slideDown' ? 'slideUp' : 'slideDown';
  }

  /**
   * Navigates to the details page for the puzzle that matches the search query.
   */
  moveToDetails(){
    let puzzle = this.puzzles?.find(puzzle => puzzle.name === this.search)
    if(puzzle) {
      this.puzzleService.setCurrentPuzzle(puzzle)
      this.router.navigate([`puzzles/info`]).then(r => console.log(r))
    }
  }

  /**
   * Adds the given puzzle to the cart and opens the modal.
   * @param puzzle the puzzle to add to the cart
   * @param template the template to use for the modal
   * @return the modal reference
   */
  addToCart(puzzle: Puzzle, template: TemplateRef<void>){
    if(!this.accountService.isLoggedInUser()){
      this.snackBar.open("You must be logged in to add a puzzle to your cart", "Go to login", {
        duration: 5000,
      }).onAction().subscribe(
        () => this.router.navigate(['/login']).then(r => console.log(r))
      );

      return
    }
    let accountId = this.accountService.getLoggedInAccount()?.id;
    if(accountId){
      this.cartService.addItemToCart(accountId, puzzle.id).pipe(
        catchError((err) => {
          this.snackBar.open("Cannot add to cart. Quantity in cart is equal to quantity in stock.", "Close", {
              duration: 5000,
            })
          return []
          }
        )
      ).subscribe(
        (cart) => {
          console.log("Puzzle added to cart")
          this.openModal(template)
        }
      )
    }

  }
  login(){
    this.router.navigate(['/login']).then(r => console.log(r))
  }
  logout() {
    this.accountService.logout()
    this.router.navigate(['/login']).then(r => console.log(r))
  }
  isLoggedInUser() {
    return this.accountService.isLoggedInUser()
  }

  setCurrentPuzzle(puzzle: Puzzle){
    this.puzzleService.setCurrentPuzzle(puzzle)
  }
}
