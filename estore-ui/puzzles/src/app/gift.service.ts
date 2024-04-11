import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Gift} from "./gift";

@Injectable({
  providedIn: 'root'
})
export class GiftService {

  constructor(private httpClient: HttpClient) {
  }

  sendGift(gift: Gift) {
    return this.httpClient.post<Gift>('http://localhost:8080/gifts', gift);
  }
}
