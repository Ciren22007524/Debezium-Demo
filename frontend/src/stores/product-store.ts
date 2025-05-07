import { defineStore } from 'pinia';
import { ref } from 'vue';
import { api } from 'boot/axios';
import { Product } from 'src/types';
import { AxiosResponse } from 'axios';

export const useProductStore = defineStore('product', () => {
  const products = ref<Product[]>([]);
  const loading = ref(false);

  async function fetchProducts() {
    loading.value = true;
    try {
      const res: AxiosResponse<Product[]> = await api.get('/products');
      console.log(res.data);
      products.value = res.data;
    } catch (err) {
      console.log(err);
    } finally {
      loading.value = false;
    }
  }

  return {
    products,
    loading,
    fetchProducts
  };
});
