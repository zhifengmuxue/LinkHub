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
            <a-menu-item key="url-shortener">
              <template #icon>
                <LinkOutlined />
              </template>
              <span v-if="!collapsed">link & QR</span>
            </a-menu-item>
            <a-menu-item key="btn" @click="toggleCollapsed">
                <template #icon>
                <MenuUnfoldOutlined v-if="collapsed" />
                <MenuFoldOutlined v-else />
                </template>
                <span v-if="!collapsed">{{ collapsed ? '展开' : '收起' }}</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
  
        <!-- 右侧内容区域 -->
        <a-layout-content style="padding: 24px; overflow-y: auto;">
          <ShortyQR />
        </a-layout-content>
      </a-layout>
    </a-layout>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue';
  import { LinkOutlined, MenuFoldOutlined,MenuUnfoldOutlined } from '@ant-design/icons-vue';
  import ShortyQR from '@/compents/tools/ShortyQR.vue';
  
  const collapsed = ref(true);
  const selectedKeys = ref<string[]>(['url-shortener']);
  
  const handleMenuClick = ({ key }: { key: string }) => {
    selectedKeys.value = [key];
  };

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
  }
  </style>