<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated class="bg-white text-black">
      <q-toolbar>

        <!-- 漢堡按鈕：控制 drawer -->
        <q-btn
          flat
          dense
          round
          icon="menu"
          @click="toggleLeftDrawer"
          class="q-mr-sm"
        />

        <!-- Quasar logo：回首頁 -->
        <q-btn
          flat
          dense
          round
          tag="router-link"
          to="/"
        >
          <q-avatar size="42px">
            <img src="/icons/favicon-128x128.png" alt="icon"/>
          </q-avatar>
        </q-btn>

        <q-toolbar-title class="text-weight-bold">Microservice Dashboard</q-toolbar-title>

        <q-space />

        <q-input
          v-model="search"
          dense
          outlined
          rounded
          placeholder="Search..."
          class="q-mr-md"
        />
        <q-btn dense flat round icon="notifications" />
        <q-btn dense flat round icon="settings" />

      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      class="bg-primary text-white"
      bordered
    >
      <q-list>
        <q-item
          clickable
          tag="router-link"
          to="/"
          class="q-py-md"
        >
          <q-item-section avatar>
            <q-avatar size="42px">
              <img src="/icons/favicon-128x128.png" alt="icon"/>
            </q-avatar>
          </q-item-section>
          <q-item-section>
            <q-item-label class="text-weight-bold">Quasar</q-item-label>
            <q-item-label caption>Home</q-item-label>
          </q-item-section>
        </q-item>

        <q-separator />

        <q-item
          v-for="link in linksList"
          :key="link.title"
          :to="link.link"
          clickable
          active-class="bg-white text-primary"
          tag="router-link"
        >
          <q-item-section avatar>
            <q-icon :name="link.icon" />
          </q-item-section>

          <q-item-section>
            <q-item-label>{{ link.title }}</q-item-label>
            <q-item-label caption>{{ link.caption }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { EssentialLinkProps } from 'components/EssentialLink.vue';

defineOptions({
  name: 'MainLayout'
});

const linksList: EssentialLinkProps[] = [
  {
    title: 'Product',
    caption: 'Product Service',
    icon: 'shopping_cart',
    link: '/products' // ← 改成內部路由
  },
  {
    title: 'Order',
    caption: 'Order Service',
    icon: 'receipt_long',
    link: '/orders'
  },
  {
    title: 'Point',
    caption: 'Point System',
    icon: 'emoji_events',
    link: '/points'
  }
];

const leftDrawerOpen = ref(false);
const search = ref('');

function toggleLeftDrawer () {
  leftDrawerOpen.value = !leftDrawerOpen.value;
}
</script>
