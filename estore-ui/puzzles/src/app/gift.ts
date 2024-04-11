import {Order} from "./order";

export interface Gift{
  order: Order,
  recipientEmail: string,
  message: string
}
