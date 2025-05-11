<template>
  <q-page class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">訂單列表</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <template v-if="!loading && orders.length">
          <q-table
            :rows="orders"
            :columns="columns"
            row-key="id"
            flat
            bordered
          />
        </template>

        <template v-else-if="!loading && orders.length === 0">
          <div class="q-pa-md text-grey">
            <q-icon name="warning" size="sm" color="orange" class="q-mr-sm" />
            目前沒有訂單
          </div>
        </template>

        <q-inner-loading :showing="loading" />
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { Order, TableColumn } from 'src/types'
import { onMounted } from 'vue'
import { useOrderStore } from 'stores/order-store'
import { storeToRefs } from 'pinia';

defineOptions({ name: 'OrderListPage' })
const orderStore = useOrderStore()
const { orders, loading } = storeToRefs(orderStore)
const { fetchOrders } = orderStore

const columns: TableColumn<Order>[] = [
  {
    name: 'id',
    label: '訂單編號',
    field: 'id',
    align: 'center',
    style: 'width: 25%',
    headerStyle: 'width: 25%'
  },
  {
    name: 'status',
    label: '狀態',
    field: 'status',
    align: 'center',
    style: 'width: 25%',
    headerStyle: 'width: 25%'
  },
  {
    name: 'totalAmount',
    label: '總金額',
    field: 'totalAmount',
    align: 'center',
    style: 'width: 25%',
    headerStyle: 'width: 25%'
  },
  {
    name: 'createdAt',
    label: '建立時間',
    field: 'createdAt',
    align: 'center',
    style: 'width: 25%',
    headerStyle: 'width: 25%'
  }
]

onMounted(() => {
  fetchOrders()
})
</script>
