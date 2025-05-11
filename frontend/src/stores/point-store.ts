import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { api } from 'boot/axios'
import { PointTransaction } from 'src/types'

export const usePointStore = defineStore('point', () => {
  const points = ref<PointTransaction[]>([])
  const loading = ref(false)

  async function fetchPoints() {
    loading.value = true
    try {
      const res = await api.get('/points')
      points.value = res.data
    } catch (err) {
      console.error('取得點數紀錄失敗', err)
    } finally {
      loading.value = false
    }
  }

  const totalPoints = computed(() => {
    return points.value.reduce((sum, pt) => {
      return pt.type === 'EARN' ? sum + pt.points : sum - pt.points
    }, 0)
  })

  return { points, loading, fetchPoints, totalPoints }
})
