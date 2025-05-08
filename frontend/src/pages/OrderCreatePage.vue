<template>
  <q-page padding>
    <q-btn label="建立訂單" color="primary" @click="createOrder" class="q-mb-md" />
    <q-table
      :rows="cartItems"
      :columns="columns"
      row-key="id"
      flat
      bordered
    >
      <!-- 圖片欄位 slot -->
      <template v-slot:body-cell-imgUrl="props">
        <q-td :props="props">
          <q-img :src="props.row.imgUrl" style="width: 80px; height: auto;" />
        </q-td>
      </template>

      <!-- 數量欄位 slot -->
      <template v-slot:body-cell-quantity="props">
        <q-td :props="props">
          <q-input
            v-model.number="quantities[props.row.id]"
            type="number"
            dense
            min="1"
            :max="props.row.stock"
            style="width: 80px"
          />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { api } from 'boot/axios'
import { useCartStore } from 'stores/cart-store'
import type { CartItem, ProductOrderRequest, TableColumn } from 'src/types'
import { Notify } from 'quasar'
import { AxiosError } from 'axios';

const cartStore = useCartStore()
const cartItems = cartStore.cartItems;
const quantities = ref<Record<number, number>>({})

// 表格欄位設定
const columns: TableColumn<CartItem>[] = [
  { name: 'imgUrl', label: '圖片', field: 'imgUrl', align: 'left' },
  { name: 'name', label: '名稱', field: 'name', align: 'left' },
  { name: 'price', label: '價格', field: 'price', align: 'right' },
  { name: 'quantity', label: '數量', field: 'quantity', align: 'right' }
]

// 初始化每個商品的數量
onMounted(() => {
  cartItems.forEach(item => {
    quantities.value[item.id] = item.quantity;
  });
});

// 建立訂單
const createOrder = async () => {
  const payload: ProductOrderRequest[] = cartItems.map(item => ({
    productId: item.id,
    quantity: quantities.value[item.id] || 1,
    price: item.price
  }))

  try {
    await api.post('/orders', payload)
    Notify.create({
      message: '訂單已成功建立！',
      color: 'green',
      icon: 'check'
    })
    cartStore.clearCart()
  } catch (err) {
    const error = err as AxiosError<{ message?: string }>
    const msg = error.response?.data?.message || '請稍後再試'
    Notify.create({
      message: '訂單建立失敗: ' + msg,
      color: 'red',
      icon: 'error'
    })
  }
}
</script>
