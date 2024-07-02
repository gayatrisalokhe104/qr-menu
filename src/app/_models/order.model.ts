import { orderFood } from "./order-food.model";

export class Order  {
    customerName!: string;
    phone!: number;
    totalAmount!: number;
    tableNumber!: number;
    orderFoods!: orderFood[];
    token!: any;
}