<template>
<div class="site-container">
    <div class="site-grid">
    <a-card 
        v-for="site in sites" 
        :key="site.id"
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
        :total="totalSites"
        class="pagination"
        @change="handlePageChange"
    />
</div>
</template>

<script lang="ts" setup>
import { ref, watch, computed } from 'vue';
import axios from 'axios';

interface Site {
  id: number;
  name: string;
  url: string;
  icon: string;
  description: string;
}

// 测试数据
const mockSites = [
  {
    id: 1,
    name: 'GitHub',
    url: 'https://github.com',
    icon: 'https://github.githubassets.com/favicons/favicon.png',
    description: '代码托管平台'
  },
  {
    id: 2,
    name: 'Vue.js',
    url: 'https://vuejs.org',
    icon: 'https://vuejs.org/images/logo.png',
    description: '渐进式JavaScript框架'
  },
  // 添加更多测试数据...
];

const handlePageChange = (page: number) => {
  currentPage.value = page;
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

const props = defineProps<{
  category: string;
  collapsed: boolean;
}>();

const sites = ref<Site[]>([]);
const totalSites = ref(0);
const loading = ref(false);

const openSite = (url: string) => {
  window.open(url, '_blank');
};

const fetchSitesFromBackend = async () => {
  try {
    loading.value = true;
    const response = await axios.get('/api/sites', {
      params: {
        category: props.category,
        page: currentPage.value,
        size: pageSize.value
      }
    });
    sites.value = response.data.content;
    totalSites.value = response.data.totalElements;
  } catch (error) {
    console.error('请求失败，使用测试数据', error);
    sites.value = mockSites;
    totalSites.value = mockSites.length;
  } finally {
    loading.value = false;
  }
};

// 监听 category 变化
watch(() => props.category, fetchSitesFromBackend, { immediate: true });

// 分页相关
const currentPage = ref(1);
const pageSize = computed(() => props.collapsed ? 16 : 15);
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