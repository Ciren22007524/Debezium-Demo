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
      class="custom-drawer"
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
            <q-item-label caption class="text-white">Home</q-item-label>
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
          class="menu-link"
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
<style scoped>
.custom-drawer {
  background-color: #2F3A56;
  color: #ECEFF4;
  border-right: 3px solid #1E263A;
}

.custom-drawer .q-item {
  transition: background-color 0.2s;
}

.custom-drawer .q-item:hover {
  background-color: #3A4763;
}

.custom-drawer .q-item.q-router-link--active {
  background-color: #1E263A;
  color: #00B4FF;
}
.custom-drawer .menu-link {
  transition: background-color 0.2s;
}

.custom-drawer .menu-link:hover {
  background-color: #3A4763;
}

.custom-drawer .menu-link.q-router-link--active {
  background-color: #1E263A;
  color: #00B4FF;
}

.menu-link:hover:not(.bg-white):not(.text-primary) {
  background-color: #e0e0e0;
  color: inherit;
}

.menu-link:hover:not(.bg-white):not(.text-primary) .q-item__label,
.menu-link:hover:not(.bg-white):not(.text-primary) .q-icon {
  color: inherit;
}

.menu-link.bg-white.text-primary:hover {
  background-color: white !important;
  color: #1976d2 !important;
}

.menu-link.bg-white.text-primary:hover .q-item__label,
.menu-link.bg-white.text-primary:hover .q-icon {
  color: #1976d2 !important;
}
</style>
