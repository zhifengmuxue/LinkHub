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
  {
    id: 3,
    name: 'Dribbble',
    url: 'https://dribbble.com',
    icon: 'https://cdn.dribbble.com/assets/favicon-b38525134603b9513174ec887944bde1a869eb6cd414f4d640ee48ab2a15a26b.ico',
    description: '设计师社区'
  },
  {
    id: 4,
    name: 'Google',
    url: 'https://google.com',
    icon: 'https://www.google.com/favicon.ico',
    description: '全球搜索引擎'
  },
  {
    id: 5,
    name: 'YouTube',
    url: 'https://youtube.com',
    icon: 'https://www.youtube.com/favicon.ico',
    description: '视频分享平台'
  },
  {
    id: 6,
    name: 'Twitter',
    url: 'https://twitter.com',
    icon: 'https://abs.twimg.com/favicons/twitter.ico',
    description: '社交媒体平台'
  },
  {
    id: 7,
    name: 'Facebook',
    url: 'https://facebook.com',
    icon: 'https://static.xx.fbcdn.net/rsrc.php/yD/r/d4ZIVX-5C-b.ico',
    description: '社交网络'
  },
  {
    id: 8,
    name: 'Instagram',
    url: 'https://instagram.com',
    icon: 'https://www.instagram.com/static/images/ico/favicon.ico',
    description: '图片分享平台'
  },
  {
    id: 9,
    name: 'LinkedIn',
    url: 'https://linkedin.com',
    icon: 'https://static.licdn.com/sc/h/akt4ae504epesldzj74dzred8',
    description: '职业社交平台'
  },
  {
    id: 10,
    name: 'Reddit',
    url: 'https://reddit.com',
    icon: 'https://www.redditstatic.com/desktop2x/img/favicon/favicon-32x32.png',
    description: '社交新闻平台'
  },
  {
    id: 11,
    name: 'Netflix',
    url: 'https://netflix.com',
    icon: 'https://assets.nflxext.com/us/ffe/siteui/common/icons/nficon2016.ico',
    description: '流媒体平台'
  },
  {
    id: 12,
    name: 'Spotify',
    url: 'https://spotify.com',
    icon: 'https://open.spotifycdn.com/cdn/images/favicon32.b64ecc03.png',
    description: '音乐流媒体平台'
  },
  {
    id: 13,
    name: 'Stack Overflow',
    url: 'https://stackoverflow.com',
    icon: 'https://cdn.sstatic.net/Sites/stackoverflow/Img/favicon.ico',
    description: '开发者问答社区'
  },
  {
    id: 14,
    name: 'Medium',
    url: 'https://medium.com',
    icon: 'https://miro.medium.com/v2/resize:fill:64:64/1*sHhtYhaCe2Uc3IU0IgKwIQ.png',
    description: '内容创作平台'
  },
  {
    id: 15,
    name: 'Notion',
    url: 'https://notion.so',
    icon: 'https://www.notion.so/images/favicon.ico',
    description: '笔记与任务管理工具'
  },
  {
    id: 16,
    name: 'Notion',
    url: 'https://notion.so',
    icon: 'https://www.notion.so/images/favicon.ico',
    description: '笔记与任务管理工具'
  },
  {
    id: 17,
    name: 'Notion',
    url: 'https://notion.so',
    icon: 'https://www.notion.so/images/favicon.ico',
    description: '笔记与任务管理工具'
  }
];


const props = defineProps<{
  category: string;
  collapsed: boolean;
}>();

// 分页相关
const currentPage = ref(1);
const pageSize = computed(() => props.collapsed ? 16 : 12);
const sites = ref<Site[]>([]);
const totalSites = ref(0);
const loading = ref(false);

const openSite = (url: string) => {
  window.open(url, '_blank');
};

// const fetchSitesFromBackend = async () => {
//   try {
//     loading.value = true;
//     const response = await axios.get('/api/sites', {
//       params: {
//         category: props.category,
//         page: currentPage.value,
//         size: pageSize.value
//       }
//     });
//     sites.value = response.data.content;
//     totalSites.value = response.data.totalElements;
//   } catch (error) {
//     console.error('请求失败，使用测试数据', error);
//     sites.value = mockSites;
//     totalSites.value = mockSites.length;
//   } finally {
//     loading.value = false;
//   }
// };

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchSitesFromBackend(); // 切换页码时重新加载数据
  window.scrollTo({ top: 0, behavior: 'smooth' }); // 滚动到顶部
};

const fetchSitesFromBackend = async () => {
  try {
    loading.value = true;
    // 模拟后端请求延迟
    await new Promise(resolve => setTimeout(resolve, 50));

    // 直接使用全部数据，并根据分页参数切片
    const startIndex = (currentPage.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    sites.value = mockSites.slice(startIndex, endIndex);
    totalSites.value = mockSites.length;
  } catch (error) {
    console.error('请求失败，使用测试数据', error);
    const startIndex = (currentPage.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    sites.value = mockSites.slice(startIndex, endIndex);
    totalSites.value = mockSites.length;
  } finally {
    loading.value = false;
  }
};

// 监听 category 变化
watch(() => props.category, fetchSitesFromBackend, { immediate: true });
watch(() => props.collapsed, () => {
  fetchSitesFromBackend(); // collapsed 变化时重新加载数据
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