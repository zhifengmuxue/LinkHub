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
          v-model:selectedKeys="selectedKeys2"
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
        >
          <a-menu-item key="1">
            <template #icon>
              <RadarChartOutlined />
            </template>
            <span v-if="!collapsed">option1</span>
          </a-menu-item>
          <a-menu-item key="2">
            <template #icon>
              <AppstoreOutlined />
            </template>
            <span v-if="!collapsed">option2</span>
          </a-menu-item>
          <a-menu-item key="3">
            <template #icon>
              <laptop-outlined />
            </template>
            <span v-if="!collapsed">option2</span>
          </a-menu-item>
          <a-menu-item key="4" @click="toggleCollapsed">
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
  MenuUnfoldOutlined, MenuFoldOutlined 
} from '@ant-design/icons-vue';
import SiteGrid from '@/compents/SIteGrid.vue';
const currentCategory = ref('technology')

const selectedKeys2 = ref<string[]>(['1']);
const collapsed = ref(true);

const toggleCollapsed = () => {
  collapsed.value = !collapsed.value;
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