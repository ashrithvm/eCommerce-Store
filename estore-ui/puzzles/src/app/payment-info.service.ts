import {Injectable} from "@angular/core";
import {PaymentInfo} from "./payment-info";
import {HttpClient} from "@angular/common/http";
import {AccountService} from "./account.service";


@Injectable({
  providedIn: 'root'
})
export class PaymentInfoService{
  private baseUrl = 'http://localhost:8080/paymentInfos';
  constructor(private httpClient: HttpClient, private accountService: AccountService) { }


  /**
   * Makes a request to the server to get all payment info.
   * @returns an observable of the payment info
   */
  getAll(){
    return this.httpClient.get<PaymentInfo[]>(`${this.baseUrl}/${this.accountService.getLoggedInAccount()!!.id}`)
  }

  /**
   * Makes a request to the server to create a payment info.
   * @param paymentInfo the payment info to create
   * @returns an observable of the payment info
   */
  create(paymentInfo: PaymentInfo) {
    return this.httpClient.post<PaymentInfo>(this.baseUrl, paymentInfo)
  }


}
