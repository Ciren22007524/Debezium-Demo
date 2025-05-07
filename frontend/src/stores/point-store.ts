// src/stores/point-store.ts
import { defineStore } from 'pinia';
import { ref } from 'vue';
import { api } from 'boot/axios';
import {PointTransaction} from 'src/types';
import { AxiosResponse } from 'axios';

export const usePointStore = defineStore('point', () => {
  const points = ref<PointTransaction[]>([]);
  const loading = ref(false);

  async function fetchPoints() {
    loading.value = true;
    try {
      const res: AxiosResponse<PointTransaction[]> = await api.get('/points');
      points.value = res.data;
    } finally {
      loading.value = false;
    }
  }

  return { points, loading, fetchPoints };
});
