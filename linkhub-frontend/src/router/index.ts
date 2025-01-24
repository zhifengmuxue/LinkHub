import { createRouter, createWebHistory } from 'vue-router'
import LinkhubView from '@/views/LinkhubView.vue'
import HomeView from '@/views/HomeView.vue'
import ToolView from '@/views/ToolView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: HomeView,
    },
    {
      path: '/linkhub',
      component: LinkhubView,
    },
    {
      path: '/tools',
      component: ToolView,
    }
  ],
})

export default router
