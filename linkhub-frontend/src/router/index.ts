import { createRouter, createWebHistory } from 'vue-router'
import LinkhubView from '@/views/LinkhubView.vue'
import HomeView from '@/views/HomeView.vue'
import OtherView from '@/views/OtherView.vue'
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
      path: '/other',
      component: OtherView,
    },
  ],
})

export default router
