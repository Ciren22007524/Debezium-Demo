<template>
  <q-page class="q-pa-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">商品列表</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <q-table
          :rows="products"
          :columns="columns"
          row-key="id"
          flat
          bordered
          :loading="loading"
          v-if="products.length"
        />
        <div v-else-if="!loading" class="q-pa-md text-grey">
          <q-icon name="warning" size="sm" color="orange" class="q-mr-sm" />
          目前沒有商品
        </div>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
import { Product, TableColumn } from 'src/types';

defineOptions({
  name: 'ProductListPage'
});

import { onMounted } from 'vue';
import { useProductStore } from 'stores/product-store';

const { products, loading, fetchProducts } = useProductStore();

const columns: TableColumn<Product>[] = [
  { name: 'id', label: 'ID', field: 'id', align: 'left' },
  { name: 'name', label: '商品名稱', field: 'name', align: 'left' },
  { name: 'price', label: '價格', field: 'price', align: 'right' },
  { name: 'stock', label: '庫存', field: 'stock', align: 'right' }
];

onMounted(() => {
  fetchProducts();
});
</script>
