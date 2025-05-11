<template>
  <q-page class="q-pa-md flex flex-center column">
    <div class="text-h6 q-mb-md">demo架構</div>
    <q-card flat bordered style="width: 100%;">
      <q-img
        src="/images/architecture.jpg"
        alt="System Architecture"
        style="width: 100%;"
        contain
      />
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
defineOptions({
  name: 'IndexPage'
});
</script>
