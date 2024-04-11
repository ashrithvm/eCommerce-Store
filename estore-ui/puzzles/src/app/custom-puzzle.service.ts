import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomPuzzle} from "./custom_puzzle";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CustomPuzzleService {
  private baseUrl = 'http://localhost:8080/customPuzzles'
  constructor(private httpClient: HttpClient) { }

  getByAccountId(accountId: number): Observable<CustomPuzzle[]> {
    return this.httpClient.get<CustomPuzzle[]>(`${this.baseUrl}/${accountId}`)
  }

  create (customPuzzle: CustomPuzzle): Observable<CustomPuzzle> {
    return this.httpClient.post<CustomPuzzle>(this.baseUrl, customPuzzle)
  }

  delete (id: number): Observable<any> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`)
  }
}
