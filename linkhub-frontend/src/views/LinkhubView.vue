<template>
  <a-layout style="height: 100vh;">
    <a-layout style="height: calc(100vh - 64px);">
      <!-- 侧边栏 -->
      <a-layout-sider 
        v-model:collapsed="collapsed"
        collapsible
        :trigger="null"
        width="200"
        style="background: #fff"
      >
        <a-menu
          v-model:selectedKeys="selectedKeys"
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleMenuClick"
        >
          <a-menu-item key="0">
            <template #icon>
              <HomeOutlined />
            </template>
            <span v-if="!collapsed">全部</span>
          </a-menu-item>
          <a-menu-item key="1">
            <template #icon>
              <RadarChartOutlined />
            </template>
            <span v-if="!collapsed">盖勒瑞</span>
          </a-menu-item>
          <a-menu-item key="2">
            <template #icon>
              <AppstoreOutlined />
            </template>
            <span v-if="!collapsed">开发工具</span>
          </a-menu-item>
          <a-menu-item key="3">
            <template #icon>
              <BookOutlined />
            </template>
            <span v-if="!collapsed">官方文档</span>
          </a-menu-item>
          <a-menu-item key="4">
            <template #icon>
              <laptop-outlined />
            </template>
            <span v-if="!collapsed">博客站点</span>
          </a-menu-item>
          <a-menu-item key="btn" @click="toggleCollapsed">
            <template #icon>
              <menu-unfold-outlined v-if="collapsed" />
              <menu-fold-outlined v-else />
            </template>
            <span v-if="!collapsed">{{ collapsed ? '展开' : '收起' }}</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <!-- 内容区域 -->
      <a-layout style="padding: 0;">
        <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: 'calc(100vh - 64px - 24px)' }"
        >
          <SiteGrid 
          :category="currentCategory"
          :collapsed="collapsed" 
          />
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { 
  LaptopOutlined, AppstoreOutlined,RadarChartOutlined,
  MenuUnfoldOutlined, MenuFoldOutlined , HomeOutlined ,BookOutlined
} from '@ant-design/icons-vue';
import SiteGrid from '@/compents/SiteGrid.vue';


const categoryMap = {
  '0': '',              // 全部
  '1': 'gallery',  // 技术
  '2': 'tools',       // 工具
  '3': 'document',       // 文档
  '4': 'blogs',      // 博客
  'btn': 'null'
};

const currentCategory = ref(categoryMap['0']); // 默认分类
const selectedKeys = ref<string[]>(['0']); // 默认选中第一个菜单项
const collapsed = ref(true);

const toggleCollapsed = () => {
  collapsed.value = !collapsed.value;
};

const handleMenuClick = ({ key }: { key: keyof typeof categoryMap }) => {
  if (key === 'btn') return; // 跳过 key=5 的逻辑
  selectedKeys.value = [key];
  currentCategory.value = categoryMap[key];
};
</script>

<style scoped>
/* 添加过渡效果 */
.ant-menu-item {
  transition: all 0.2s;
}

/* 优化移动端样式 */
@media (max-width: 768px) {
  .ant-layout-sider {
    position: fixed;
    z-index: 100;
  }
  
  .ant-layout-content {
    margin-left: 80px !important;
  }
}
/* 调整收起时的样式 */
.ant-layout-sider-collapsed {
  width: 80px !important;
  max-width: 80px !important;
  min-width: 80px !important;
}
</style>