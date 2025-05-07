<template>
  <q-page class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">訂單列表</div>
      </q-card-section>

      <q-card-section>
        <q-table
          :rows="orders"
          :columns="columns"
          row-key="id"
          flat
          dense
          :loading="loading"
          no-data-label="目前沒有訂單"
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { Order, TableColumn } from 'src/types'
import {useOrderStore} from "stores/order-store";
import {storeToRefs} from "pinia";

const orderStore = useOrderStore()
const { orders } = storeToRefs(orderStore)

const loading = ref(true)

const columns: TableColumn<Order>[] = [
  { name: 'id', label: '訂單編號', field: 'id', align: 'left', sortable: true },
  { name: 'status', label: '狀態', field: 'status', align: 'left' },
  { name: 'totalAmount', label: '總金額', field: 'totalAmount', align: 'right' },
  { name: 'createdAt', label: '建立時間', field: 'createdAt', align: 'left' }
]

onMounted(async () => {
  await orderStore.fetchOrders()
  loading.value = false
})

defineOptions({
  name: 'OrderListPage'
})
</script>
