<template>
  <q-drawer v-model="cartOpen" side="right" overlay behavior="mobile">
    <q-card class="q-pa-md" style="width: 300px;">
      <div class="row items-center justify-between">
        <div class="text-h6">購物車</div>
        <q-btn flat round dense icon="close" @click="cartOpen = false" />
      </div>

      <q-separator class="q-my-sm" />

      <div v-for="item in cartItems" :key="item.id" class="q-mb-md">
        <q-img :src="item.imgUrl" style="width: 100%; height: 100px" />
        <div class="row items-center justify-between q-mt-sm">
          <div>
            <div class="text-subtitle1">{{ item.name }}</div>
            <div class="text-subtitle2">NT$ {{ item.price }} × {{ item.quantity }}</div>
          </div>
          <q-btn icon="delete" flat color="red" @click="removeFromCart(item.id)" />
        </div>

      </div>

      <q-separator class="q-my-sm" />

      <div class="row items-center justify-between q-mt-sm">
        <div class="text-subtitle1 q-mr-sm">
          總金額：<span class="text-weight-bold">NT$ {{ totalAmount }}</span>
        </div>
        <q-btn label="前往結帳" color="primary" @click="goToCart" />
      </div>
    </q-card>
  </q-drawer>

  <q-page class="q-pa-md">
    <q-card>
      <q-card-section class="row items-center justify-between">
        <div class="text-h6">商品列表</div>
        <q-btn icon="shopping_cart" color="secondary" @click="cartOpen = true" label="購物車">
          <q-badge
            color="red"
            floating
            transparent
            v-if="cartStore.cartItems.length"
          >
            {{ cartStore.cartItems.length }}
          </q-badge>
        </q-btn>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <q-table
          :rows="products"
          :columns="columns"
          row-key="id || name"
          flat
          bordered
          :loading="loading"
          v-if="!!products.length"
        >
          <template v-slot:body-cell-imgUrl="props">
            <q-td :props="props">
              <q-img :src="props.row.imgUrl" style="width: 80px; height: auto;" />
            </q-td>
          </template>

          <template v-slot:body-cell-actions="props">
            <q-td :props="props">
              <q-btn color="primary" @click="addToCart(props.row)" size="sm" label="加入購物車" />
            </q-td>
          </template>
        </q-table>

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
import { onMounted, ref } from 'vue';
import { useProductStore } from 'stores/product-store';
import { storeToRefs } from 'pinia';
import { useCartStore } from 'stores/cart-store';
import { useRouter } from 'vue-router';
import { Notify } from 'quasar';

const router = useRouter();
const productStore = useProductStore();
const { products, loading } = storeToRefs(productStore);
const { fetchProducts } = productStore;

const columns: TableColumn<Product>[] = [
  {
    name: 'imgUrl',
    label: '圖片',
    field: 'imgUrl',
    align: 'center',
    style: 'width: 20%'
  },
  {
    name: 'name',
    label: '商品名稱',
    field: 'name',
    align: 'center',
    style: 'width: 20%'
  },
  {
    name: 'price',
    label: '價格',
    field: 'price',
    align: 'center',
    style: 'width: 20%'
  },
  {
    name: 'stock',
    label: '庫存',
    field: 'stock',
    align: 'center',
    style: 'width: 20%'
  },
  {
    name: 'actions',
    label: '操作',
    field: 'id',
    align: 'center',
    sortable: false,
    style: 'width: 20%'
  }
];

const cartStore = useCartStore();
function addToCart(product: Product) {
  const success = cartStore.addItem(product);

  if (success) {
    Notify.create({
      message: `${product.name} 已加入購物車`,
      color: 'green',
      icon: 'check_circle',
      position: 'bottom-right',
      timeout: 1500
    });
  } else {
    Notify.create({
      message: `已達最大庫存上限（${product.stock} 件）`,
      color: 'red',
      icon: 'warning',
      position: 'bottom-right',
      timeout: 2000
    });
  }
}

function goToCart() {
  router.push('/orders/create');
}

const cartOpen = ref(false);
const { cartItems, totalAmount } = storeToRefs(cartStore);

function removeFromCart(productId: number) {
  cartStore.removeItem(productId);
}

onMounted(() => {
  fetchProducts();
});

defineOptions({
  name: 'ProductListPage'
});
</script>
