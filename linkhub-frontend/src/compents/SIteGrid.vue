<template>
  <div class="site-container">
    <div class="site-grid">
    <a-card 
        v-for="site in sites" 
        :key="site.name"
        class="site-card"
        hoverable
        @click="openSite(site.url)"
    >
        <div class="card-content">
        <img class="site-icon" :src="site.icon" :alt="site.name" />
        <div class="site-info">
            <div class="site-name">{{ site.name }}</div>
            <div class="site-description">{{ site.description }}</div>
        </div>
        </div>
    </a-card>
    </div>
    
    <!-- 分页组件 -->
    <a-pagination
        v-model:current="currentPage"
        simple
        :total="total"
        class="pagination"
        @change="handlePageChange"
    />
</div>
</template>

<script lang="ts" setup>
import { ref, watch, computed } from 'vue';
import { onMounted } from 'vue';
import axios from 'axios';

interface Site {
  name: string;
  url: string;
  icon: string;
  description: string;
  categoryName: string;
}



const props = defineProps<{
  category: string;
  collapsed: boolean;
}>();

// 分页相关
const currentPage = ref(1);
const pageSize = computed(() => props.collapsed ? 16 : 12);
const sites = ref<Site[]>([]);
const total = ref(0);
const loading = ref(false);

const openSite = (url: string) => {
  window.open(url, '_blank');
};

const fetchSites = async () => {
  loading.value = true;
  try {
    const response = await axios.get('api/sites', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
      },
    });
    if (response.data.code === '200') {
      sites.value = response.data.data;
      total.value = response.data.total || response.data.data.length; // 根据后端返回调整
    } else {
      console.error('Failed to fetch sites:', response.data.message);
    }
  } catch (error) {
    console.error('Error fetching sites:', error);
  } finally {
    loading.value = false;
  }
};

// 动态预加载图标
const preloadIcons = (sites: Site[]) => {
  sites.forEach(site => {
    const link = document.createElement('link');
    link.rel = 'preload';
    link.href = site.icon;
    link.as = 'image';
    document.head.appendChild(link);
  });
};

onMounted(() => {
  fetchSites();
});

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchSites(); // 切换页码时重新加载数据
  window.scrollTo({ top: 0, behavior: 'smooth' }); // 滚动到顶部
};

// 监听 category 变化
watch(() => props.category, fetchSites, { immediate: true });
watch(() => props.collapsed, () => {
  fetchSites(); // collapsed 变化时重新加载数据
});
</script>

<style scoped>
.site-container {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.pagination {
  margin: 24px auto;
  text-align: center;
}
.site-grid {
display: grid;
grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
gap: 16px;
padding: 16px;
}

.site-card {
cursor: pointer;
transition: transform 0.2s;
}

.site-card:hover {
transform: translateY(-4px);
}

.card-content {
display: flex;
align-items: center;
gap: 16px;
}

.site-icon {
width: 48px;
height: 48px;
border-radius: 8px;
object-fit: cover;
}

.site-info {
flex: 1;
}

.site-name {
font-weight: bold;
font-size: 16px;
margin-bottom: 4px;
}

.site-description {
font-size: 14px;
color: #666;
line-height: 1.4;
}
</style>