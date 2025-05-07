<template>
  <q-page class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">點數紀錄</div>
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
import {PointTransaction, TableColumn} from 'src/types';

defineOptions({
  name: 'PointListPage'
});

import { onMounted } from 'vue';
import { usePointStore } from 'stores/point-store';

const { points, loading, fetchPoints } = usePointStore();

const columns: TableColumn<PointTransaction>[] = [
  { name: 'id', label: 'ID', field: 'id', align: 'left' },
  { name: 'userId', label: '使用者', field: 'userId', align: 'left' },
  { name: 'amount', label: '點數', field: 'amount', align: 'right' },
  { name: 'reason', label: '原因', field: 'reason', align: 'left' },
  { name: 'createdAt', label: '建立時間', field: 'createdAt', align: 'left' }
];

onMounted(() => {
  fetchPoints();
});
</script>
