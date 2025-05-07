<template>
  <q-page class="q-pa-md">
    <q-card v-if="order">
      <q-card-section>
        <div class="text-h6">訂單編號：#{{ order.id }}</div>
        <div class="text-subtitle2">狀態：{{ order.status }}</div>
        <div class="text-subtitle2">建立時間：{{ formatDate(order.createdAt) }}</div>
        <div class="text-subtitle2">總金額：{{ order.totalAmount }}</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <div class="text-h6">訂單項目</div>
        <q-table
          :rows="items"
          :columns="columns"
          row-key="id"
          flat
          dense
        />
      </q-card-section>
    </q-card>

    <q-spinner v-else class="q-my-xl" color="primary" size="40px" />
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { api } from 'boot/axios';
import {Order, OrderItem, TableColumn} from 'src/types';
import { AxiosResponse } from 'axios';

const route = useRoute();
const orderId = route.params.orderId as string;

const order = ref<Order | null>(null);
const items = ref<OrderItem[]>([]);

const columns: TableColumn<OrderItem>[] = [
  { name: 'productId', label: '商品 ID', field: 'productId', align: 'left' },
  { name: 'quantity', label: '數量', field: 'quantity', align: 'right' },
  { name: 'unitPrice', label: '單價', field: 'unitPrice', align: 'right' },
  { name: 'subTotal', label: '小計', field: 'subTotal', align: 'right' },
];

function formatDate(dateStr: string): string {
  return new Date(dateStr).toLocaleString();
}

onMounted(async () => {
  const [orderRes, itemsRes]: [AxiosResponse<Order>, AxiosResponse<OrderItem[]>] = await Promise.all([
    api.get<Order>(`/orders/${orderId}`),
    api.get<OrderItem[]>(`/orders/${orderId}/items`)
  ]);
  order.value = orderRes.data;
  items.value = itemsRes.data;
});
</script>
