<template>
  <a-layout style="height: 100vh;">
    <!-- 头部区域 -->
    <a-layout-header class="header">
      <div class="logo" />
      <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="horizontal"
        :style="{ lineHeight: '64px', background: '#f0f2f5' }"
        @click="handleMenuClick"
      >
        <a-menu-item key="1">首页</a-menu-item>
        <a-menu-item key="2">仓库</a-menu-item>
        <a-menu-item key="3">工具</a-menu-item>
        <a-menu-item key="4">设置</a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content>
      <router-view></router-view>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const selectedKeys = ref<string[]>(['1']);
const router = useRouter();
const route = useRoute();

const updateSelectedKeys = () => {
  const keyMap: Record<string, string> = {
    '/': '1',
    '/linkhub': '2',
    '/tools': '3',
    '/setting': '4'
  };
  selectedKeys.value = [keyMap[route.path] || '1'];
};

// 初始化时设置 selectedKeys
updateSelectedKeys();

// 监听路由变化
watch(() => route.path, updateSelectedKeys);

// 处理菜单点击事件
const handleMenuClick = ({ key }: { key: string }) => {
  switch (key) {
    case '1':
      router.push('/');
      break;
    case '2':
      router.push('/linkhub');
      break;
    case '3':
      router.push('/tools');
      break;
    case '4':
      router.push('/setting');
      break;
  }
};
</script>

<style scoped>
/* 导航栏样式 */
.header {
  background-color: #f0f2f5 !important; /* 与首页和 Linkhub 页面一致的背景色 */
  border-bottom: 1px solid #e8e8e8 !important; /* 添加浅色底部边框 */
}

.ant-menu {
  background-color: transparent !important; /* 透明背景 */
}

.ant-menu-item {
  color: #333 !important; /* 文字颜色 */
  font-weight: 500; /* 加粗文字 */
}

.ant-menu-item:hover {
  color: #1890ff !important; /* 悬停时使用主题色 */
  background-color: rgba(24, 144, 255, 0.1) !important; /* 悬停背景色 */
}

.ant-menu-item-selected {
  color: #1890ff !important; /* 选中项文字颜色 */
  border-bottom: 2px solid #1890ff !important; /* 选中项底部边框 */
}

/* 其他样式保持不变 */
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>