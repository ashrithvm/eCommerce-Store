export interface PaymentInfo {
    id: number;
    accountId: number;
    cardNumber: string;
    securityCode: number;
    expirationDate: string;
    name: string;
}
