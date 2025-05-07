<template>
  <q-page class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">訂單列表</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <q-table
          :rows="orders"
          :columns="columns"
          row-key="id"
          flat
          bordered
          :loading="loading"
          v-if="orders.length"
        />
        <div v-else-if="!loading" class="q-pa-md text-grey">
          <q-icon name="warning" size="sm" color="orange" class="q-mr-sm" />
          目前沒有訂單
        </div>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { Order, TableColumn } from 'src/types'
import { onMounted } from 'vue'
import { useOrderStore } from 'stores/order-store'

defineOptions({ name: 'OrderListPage' })

const { orders, loading, fetchOrders } = useOrderStore()

const columns: TableColumn<Order>[] = [
  { name: 'id', label: '訂單編號', field: 'id', align: 'left' },
  { name: 'status', label: '狀態', field: 'status', align: 'left' },
  { name: 'totalAmount', label: '總金額', field: 'totalAmount', align: 'right' },
  { name: 'createdAt', label: '建立時間', field: 'createdAt', align: 'left' }
]

onMounted(() => {
  fetchOrders()
})
</script>
