<template>
  <q-page padding>
    <q-table
      :rows="cartItems"
      :columns="columns"
      row-key="id"
      flat
      bordered
    >
      <template v-slot:body-cell-imgUrl="props">
        <q-td :props="props">
          <q-img :src="props.row.imgUrl" style="width: 80px; height: auto;" />
        </q-td>
      </template>
      <template v-slot:body-cell-quantity="props">
        <q-td :props="props">
          <div class="row items-center justify-center">
            <q-input
              v-model.number="quantities[props.row.id]"
              type="number"
              dense
              min="1"
              :max="props.row.stock"
              style="max-width: 80px"
              input-class="text-center"
              outlined
            />
          </div>
        </q-td>
      </template>
      <template v-slot:body-cell-stock="props">
        <q-td :props="props">
          <div class="text-right">{{ props.row.stock }}</div>
        </q-td>
      </template>
    </q-table>
    <div class="row justify-between items-center q-px-md q-pt-md q-pb-lg">
      <div class="text-subtitle1">
        總金額：<span class="text-weight-bold text-primary">NT$ {{ totalAmount }}</span>
      </div>
      <q-btn label="建立訂單" color="primary" @click="createOrder" />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useCartStore } from 'stores/cart-store'
import { useOrderStore } from 'stores/order-store'
import type { CartItem, ProductOrderRequest, TableColumn } from 'src/types'
import { Notify } from 'quasar'
import { AxiosError } from 'axios'
import { storeToRefs } from 'pinia'

const cartStore = useCartStore()
const orderStore = useOrderStore()

const { cartItems, totalAmount } = storeToRefs(cartStore)
const quantities = ref<Record<number, number>>({})

const columns: TableColumn<CartItem>[] = [
  {
    name: 'imgUrl',
    label: '圖片',
    field: 'imgUrl',
    align: 'center',
    style: '20%'
  },
  { name: 'name',
    label: '名稱',
    field: 'name',
    align: 'center',
    style: '20%'
  },
  {
    name: 'price',
    label: '價格',
    field: 'price',
    align: 'center',
    style: '20%'
  },
  {
    name: 'quantity',
    label: '數量',
    field: 'quantity',
    align: 'center',
    style: '20%'
  },
  {
    name: 'stock',
    label: '庫存',
    field: 'stock',
    align: 'center',
    style: '20%'
  }
]

// 初始化數量
onMounted(() => {
  cartItems.value.forEach((item: CartItem) => {
    quantities.value[item.id] = item.quantity
  })
})

// 建立訂單
const createOrder = async () => {
  const payload: ProductOrderRequest[] = cartItems.value.map((item: CartItem) => ({
    productId: item.id,
    quantity: quantities.value[item.id] || 1,
    price: item.price
  }))

  try {
    await orderStore.createOrder(payload)
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
