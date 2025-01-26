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
        <img class="site-icon" :src="site.icon" :alt="site.name"/>
        <div class="site-info">
            <div class="site-name">{{ site.name }}</div>
            <div class="site-description">{{ site.description }}</div>
        </div>
        </div>
    </a-card>
    </div>
  
</div>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';
import { onMounted, onUnmounted } from 'vue';
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
const pageSize = 24;
const sites = ref<Site[]>([]);
const total = ref(0);
const loading = ref(false);

const openSite = (url: string) => {
  window.open(url, '_blank');
};

const isFetchingMore = ref(false); 

const fetchSites = async (isLoadMore = false) => {
  if (loading.value || isFetchingMore.value) return;
  
  if (!isLoadMore) {
    sites.value = []; // 初始加载时清空数据
    loading.value = true;
  } else {
    isFetchingMore.value = true;
  }

  try {
    const response = await axios.get('api/sites', {
      params: {
        page: currentPage.value,
        size: pageSize,
        categoryName: props.category,
      },
    });
    if (response.data.code === 200) {
      if (isLoadMore) {
        sites.value = [...sites.value, ...response.data.resultData.records];
      } else {
        sites.value = response.data.resultData.records;
      }
      total.value = response.data.resultData.total;
      preloadIcons(response.data.resultData.records);
    }
  } catch (error) {
    console.error('Error fetching sites:', error);
  } finally {
    loading.value = false;
    isFetchingMore.value = false;
  }
};

const handleScroll = () => {
  const container = document.querySelector('.site-container');
  if (!container) return;

  const { scrollTop, scrollHeight, clientHeight } = container;
  // 调整触发加载的阈值
  if (scrollHeight - (scrollTop + clientHeight) < 50 && 
      !loading.value && 
      !isFetchingMore.value &&
      sites.value.length < total.value) {
    currentPage.value++;
    fetchSites(true);
  }
};

// 修改 onMounted 和 onUnmounted
onMounted(() => {
  fetchSites();
  const container = document.querySelector('.site-container');
  if (container) {
    container.addEventListener('scroll', handleScroll);
  }
});

onUnmounted(() => {
  const container = document.querySelector('.site-container');
  if (container) {
    container.removeEventListener('scroll', handleScroll);
  }
});

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


watch(() => props.category, () => {
  currentPage.value = 1; // 重置页码
  fetchSites(); // 重新加载数据
}, { immediate: true });

watch(() => props.collapsed, () => {
  currentPage.value = 1; // 重置页码
  fetchSites(); // 重新加载数据
});


</script>

<style scoped>
.site-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 4px; /* 保持紧凑间距 */
  padding: 4px;
  min-height: calc(100vh - 64px + 1px); /* 添加1px确保内容超出容器 */
}

.site-container {
  overflow-y: auto;
  height: calc(100vh - 64px);
  padding-bottom: 20px;
  -webkit-overflow-scrolling: touch;
  scrollbar-width: none;
}

.site-card {
  width: 320px;
  height: 120px;
  margin: 0px;
  /* box-sizing: border-box; */
}

.pagination {
  margin: 24px auto;
  text-align: center;
}

.site-container::-webkit-scrollbar {
  display: none;
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

.site-container {
  overflow-y: auto;
  height: calc(100vh - 64px); /* 根据你的布局调整 */
}

.loading-indicator {
  text-align: center;
  padding: 16px;
  color: #666;
  position: sticky;
  bottom: 0;
  background: white;
  z-index: 1;
}
</style>