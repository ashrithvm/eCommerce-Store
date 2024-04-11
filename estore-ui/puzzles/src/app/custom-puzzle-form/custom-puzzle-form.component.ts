import { Component } from '@angular/core';
import {PuzzlesService} from "../puzzles.service";
import {Puzzle} from "../puzzle";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {fadeInOutAnimation} from "../animations";
import {CustomPuzzleService} from "../custom-puzzle.service";
import {AccountService} from "../account.service";
import {CustomPuzzle} from "../custom_puzzle";


@Component({
  selector: 'app-custom-puzzle-form',
  templateUrl: './custom-puzzle-form.component.html',
  styleUrl: './custom-puzzle-form.component.css',
  animations: [fadeInOutAnimation]
})
export class CustomPuzzleFormComponent {
  protected currentPuzzle?: Puzzle
  protected price = 0
  form = this.formBuilder.group({
    name: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required]),
    difficulty: new FormControl('', [Validators.required]),
    imageURL: new FormControl('', [Validators.required])
  }
  )

  constructor(
    private formBuilder: FormBuilder,
    private customPuzzleService: CustomPuzzleService,
    private accountService: AccountService,
    private router: Router
  ){

  }

  onSubmit(){
    let account = this.accountService.getLoggedInAccount()
    if(account){
      let customPuzzle: CustomPuzzle = {
        id: 0,
        account: account,
        name: this.form.get('name')!.value!,
        quantity: parseInt(this.form.get('quantity')!.value!),
        difficulty: this.form.get('difficulty')!.value!,
        imageURL: this.form.get('imageURL')!.value!,
        price: this.price
      }
      console.log(customPuzzle)
      this.customPuzzleService.create(customPuzzle).subscribe(
        customPuzzle => {
          console.log("Custom puzzle created")
          this.router.navigate(['/cart']).then(r => console.log("Navigated to cart"))
        }
      )
    }
  }

  difficultyOnChange(){


    let difficulty = this.form.get('difficulty')!.value!
    switch(difficulty){
      case 'EASY':
        this.price = 50
        break
      case 'MEDIUM':
        this.price = 70
        break
      case 'HARD':
        this.price = 100
        break
      default:
        this.price = 0
    }

  }

}
