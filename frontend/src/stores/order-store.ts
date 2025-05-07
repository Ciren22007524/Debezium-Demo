import { defineStore } from 'pinia'
import { api } from 'boot/axios'
import { ProductOrderRequest } from 'src/types'
import type { Order } from 'src/types'

export const useOrderStore = defineStore('order', {
  state: () => ({
    orders: [] as Order[]
  }),

  actions: {
    async fetchOrders() {
      const res = await api.get('/orders')
      this.orders = res.data
    },

    async createOrder(payload: ProductOrderRequest[]) {
      await api.post('/orders', payload)
      // 重新抓取訂單列表（可選）
      await this.fetchOrders()
    }
  }
})
