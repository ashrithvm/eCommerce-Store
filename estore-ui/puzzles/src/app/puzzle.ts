export interface Puzzle {
  id:number,
  name:string,
  quantity:number,
  description:string,
  price:number,
  difficulty:"EASY" | "MEDIUM" | "HARD"
  imageURL:string

}
