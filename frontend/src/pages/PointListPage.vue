<template>
  <q-page class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">點數紀錄</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <div class="text-subtitle1 text-primary">目前總積分：{{ totalPoints }}</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <q-table
          :rows="points"
          :columns="columns"
          row-key="id"
          flat
          bordered
          :loading="loading"
          v-if="points.length"
        />
        <div v-else-if="!loading" class="q-pa-md text-grey">
          <q-icon name="warning" size="sm" color="orange" class="q-mr-sm" />
          目前沒有點數紀錄
        </div>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { onMounted } from 'vue';
import { usePointStore } from 'stores/point-store';
import { PointTransaction, TableColumn } from 'src/types';

defineOptions({ name: 'PointListPage' });

const pointStore = usePointStore();
const { points, loading, totalPoints } = storeToRefs(pointStore);

const columns: TableColumn<PointTransaction>[] = [
  {
    name: 'id',
    label: 'ID',
    field: 'id',
    align: 'center',
    style: 'width: 15%'
  },
  {
    name: 'orderId',
    label: '訂單編號',
    field: 'orderId',
    align: 'center',
    style: 'width: 15%'
  },
  {
    name: 'type',
    label: '類型',
    field: 'type',
    align: 'center',
    style: 'width: 15%'
  },
  {
    name: 'points',
    label: '點數',
    field: 'points',
    align: 'center',
    style: 'width: 15%'
  },
  {
    name: 'description',
    label: '說明',
    field: 'description',
    align: 'center',
    style: 'width: 15%'
  },
  {
    name: 'createdAt',
    label: '建立時間',
    field: 'createdAt',
    align: 'center',
    style: 'width: 25%'
  }
];

onMounted(async () => {
  await pointStore.fetchPoints();
});
</script>
