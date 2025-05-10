import { defineStore } from 'pinia'
import { ref } from 'vue'
import { api } from 'boot/axios'
import type { Order, ProductOrderRequest } from 'src/types'

export const useOrderStore = defineStore('order', () => {
  const orders = ref<Order[]>([])
  const loading = ref(false)

  async function fetchOrders() {
    loading.value = true
    try {
      const res = await api.get('/orders')
      orders.value = res.data
    } catch (err) {
      console.error(err)
    } finally {
      loading.value = false
    }
  }

  async function createOrder(payload: ProductOrderRequest[]) {
    await api.post('/orders', payload)
    await fetchOrders()
  }

  return {
    orders,
    loading,
    fetchOrders,
    createOrder
  }
})
