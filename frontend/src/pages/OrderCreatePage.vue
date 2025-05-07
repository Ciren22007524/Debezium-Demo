<template>
  <q-page padding>
    <q-btn label="建立訂單" color="primary" @click="createOrder" />
    <q-table
      :rows="productStore.products"
      :columns="columns"
      row-key="id"
      selection="multiple"
      v-model:selected="selected"
    >
      <template v-slot:body-cell-quantity="props">
        <q-input v-model.number="quantities[props.row.id]" type="number" dense />
      </template>
    </q-table>
  </q-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useProductStore } from 'stores/product-store'
import { api } from 'boot/axios'
import type { Product, ProductOrderRequest } from 'src/types'

const productStore = useProductStore()
const selected = ref<Product[]>([])
const quantities = ref<Record<number, number>>({})

const columns = [
  { name: 'id', label: 'ID', field: 'id' },
  { name: 'name', label: '名稱', field: 'name' },
  { name: 'price', label: '價格', field: 'price' },
  { name: 'quantity', label: '數量', field: 'quantity' }
]

onMounted(() => {
  productStore.fetchProducts()
})

const createOrder = async () => {
  const payload: ProductOrderRequest[] = selected.value.map(row => ({
    productId: row.id,
    quantity: quantities.value[row.id] || 1,
    price: row.price
  }))

  await api.post('/orders', payload)
}
</script>
