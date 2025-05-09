import { defineStore } from 'pinia';
import { CartItem, Product } from 'src/types';

export const useCartStore = defineStore('cart', {
  state: () => ({
    cartItems: [] as CartItem[]
  }),

  getters: {
    // 總數量（item 數 × 數量）
    totalQuantity: (state: { cartItems: CartItem[] }): number =>
      state.cartItems.reduce((sum, item) => sum + item.quantity, 0),

    // 總金額
    totalAmount: (state: { cartItems: CartItem[] }): number =>
      state.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0),
  },

  actions: {
    // 加入商品到購物車（若超過庫存限制，回傳 false）
    addItem(product: Product): boolean {
      const existing = this.cartItems.find(item => item.id === product.id);
      if (existing) {
        if (existing.quantity < product.stock) {
          existing.quantity += 1;
          return true;
        }
        return false;
      }
      this.cartItems.push({ ...product, quantity: 1 });
      return true;
    },

    // 調整指定商品數量（用在結帳頁面輸入數量時）
    updateQuantity(productId: number, quantity: number): boolean {
      const item = this.cartItems.find(item => item.id === productId);
      if (!item) return false;
      if (quantity < 1 || quantity > item.stock) return false;
      item.quantity = quantity;
      return true;
    },

    // 移除指定商品
    removeItem(productId: number): void {
      this.cartItems = this.cartItems.filter(item => item.id !== productId);
    },

    // 清空購物車
    clearCart(): void {
      this.cartItems = [];
    }
  }
});
