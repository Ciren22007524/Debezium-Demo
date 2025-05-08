export interface Product {
  id: number
  version: number
  name: string
  description: string
  price: number
  stock: number
  status: string
  imgUrl?: string
  createdAt: string
  updatedAt: string
}

export interface ProductOrderRequest {
  productId: number
  quantity: number
  price: number
}

export interface Order {
  id: number;
  status: string;
  totalAmount: number;
  createdAt: string;
}

export interface OrderItem {
  id: number;
  productId: number;
  quantity: number;
  unitPrice: number;
  subTotal: number;
}

export interface PointTransaction {
  id: number;
  userId: string;
  amount: number;
  reason: string;
  createdAt: string;
}

export type TableColumn<T> = {
  name: string;
  label: string;
  field: keyof T | ((row: T) => unknown);
  required?: boolean;
  align?: 'left' | 'right' | 'center';
  sortable?: boolean;
  sort?: (a: T, b: T, rowA: T, rowB: T) => number;
  format?: (val: unknown, row?: T) => string;
  style?: string | ((row: T) => string);
  classes?: string | ((row: T) => string);
  headerStyle?: string;
  headerClasses?: string;
};

export interface CartItem extends Product {
  quantity: number;
}
