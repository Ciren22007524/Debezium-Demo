import { defineStore } from 'pinia';
import { ref } from 'vue';
import { api } from 'boot/axios';
import { PointTransaction } from 'src/types';

export const usePointStore = defineStore('point', () => {
  const points = ref<PointTransaction[]>([]);
  const loading = ref(false);

  async function fetchPoints() {
    loading.value = true;
    try {
      const res = await api.get('/points');
      points.value = res.data;
    } catch (err) {
      console.error('取得點數紀錄失敗', err);
    } finally {
      loading.value = false;
    }
  }

  return { points, loading, fetchPoints };
});
