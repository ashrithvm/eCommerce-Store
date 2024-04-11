import {Account} from "./account";

export interface CustomPuzzle{
  id: number;
  account: Account;
  name: string;
  quantity: number;
  difficulty: string;
  price: number;
  imageURL: string;
}
