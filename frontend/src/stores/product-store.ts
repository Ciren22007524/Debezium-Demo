import { defineStore } from 'pinia'
import { api } from 'boot/axios'
import { Product } from 'src/types';

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [] as Product[],
    loading: false
  }),

  actions: {
    async fetchProducts() {
      this.loading = true
      try {
        const res = await api.get('/products')
        this.products = res.data
      } finally {
        this.loading = false
      }
    }
  }
})
