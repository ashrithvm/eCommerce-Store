import { Component } from '@angular/core';
import {PuzzlesService} from "../puzzles.service";
import {Puzzle} from "../puzzle";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {fadeInOutAnimation} from "../animations";

@Component({
  selector: 'app-puzzle-edit-form',
  templateUrl: './puzzle-edit-form.component.html',
  styleUrl: './puzzle-edit-form.component.css',
  animations: [fadeInOutAnimation]
})
export class PuzzleEditFormComponent {
  protected currentPuzzle?: Puzzle
  form = this.formBuilder.group({
    name: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price:  new FormControl('', [Validators.required, Validators.pattern('^[0-9]+(\.[0-9]{1,2})?$')]),
    difficulty: new FormControl('', [Validators.required]),
    imageURL: new FormControl('', [Validators.required])

  })

  constructor(
    private puzzleService: PuzzlesService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder)
  {
    if(this.determineOperation() === 'edit') {
      let id: number = parseInt(this.route.snapshot.paramMap.get('id')!)
      console.log(this.route.snapshot.url[0].path)
      this.puzzleService.getPuzzle(id)
        .subscribe(puzzle => {
          this.currentPuzzle = puzzle
          this.form.setValue(
            {
              name: puzzle.name,
              quantity: puzzle.quantity.toString(),
              description: puzzle.description,
              price: puzzle.price.toString(),
              difficulty: puzzle.difficulty,
              imageURL: puzzle.imageURL
            })
        })
    }
    else{
      console.log("Add Puzzle form showed.")
    }
  }

  protected isCreateForm(){
    return this.determineOperation() === 'add'
  }

  private determineOperation(){
    if(this.route.snapshot.url[0].path === 'puzzle-edit-form'){
      return 'edit'
    }
    return 'add'
  }

  protected onSubmit(){
      let puzzle: Puzzle = {
          id: 0,
          name: this.form.value.name!,
          quantity: parseInt(this.form.value.quantity!),
          description: this.form.value.description!,
          price: parseFloat(this.form.value.price!),
          difficulty: this.form.value.difficulty! as "EASY" | "MEDIUM" | "HARD",
          imageURL: this.form.value.imageURL!
        }
        if(this.isCreateForm())
          this.createPuzzle(puzzle)
        else {
          puzzle.id = this.currentPuzzle!.id
          this.updatePuzzle(puzzle)
        }

        this.router.navigate(['/admin']).then(r => console.log(r))

  }

  private createPuzzle (puzzle: Puzzle){
    console.log(`Creating puzzle with name ${puzzle.name}`)
    this.puzzleService.createPuzzle(puzzle).subscribe({
      next: () => console.log('Puzzle created'),
      error: error => {
        if(error.status === 409){
          this.moveToUpdateOnError(puzzle)
        }
      }
    })
  }
   private  updatePuzzle(puzzle: Puzzle){
    console.log(`Updating puzzle with id ${puzzle.id} to ${puzzle.name}`)
    this.puzzleService.updatePuzzle(puzzle).subscribe(
      () => console.log('Puzzle updated'),
      error => {
        console.log('Error updating puzzle', error)

      }
    )
  }

  private moveToUpdateOnError(puzzle: Puzzle){
    console.log(`Puzzle with name ${puzzle.name} already exists. Moving to update form.`)
    this.puzzleService.getPuzzleByPartialName(puzzle.name).subscribe(
      puzzles => {
        for(let p of puzzles){
          if(p.name === puzzle.name){
            let confirmation = confirm(`Puzzle with name ${p.name} already exists. Do you want to update it?`)
            if(confirmation)
              this.router.navigate([`/puzzle-edit-form/${p.id}`]).then(r => console.log(r))
            break;
          }
        }
      }
    )
    }

}
