import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "./order";
@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUrl = 'http://localhost:8080/orders'
  constructor(private httpClient: HttpClient) { }

    getOrders(){
      return this.httpClient.get<Order[]>(this.baseUrl)
    }

    getOrdersByAccountId(accountId: number){
      return this.httpClient.get<Order[]>(`${this.baseUrl}/${accountId}`);
    }

    createOrder(order: Order){
      console.log("Reached createOrder in order.service.ts")
      return this.httpClient.post<Order>(this.baseUrl, order)
    }


}
