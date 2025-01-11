<template>
  <!-- 外层布局容器，设置为全屏高度 -->
  <a-layout style="height: 100vh;">
    <!-- 头部区域 -->
    <a-layout-header class="header">
      <div class="logo" />

      <a-menu
        v-model:selectedKeys="selectedKeys1"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
        @click="handleMenuClick"
      >
        <a-menu-item key="1">首页</a-menu-item>
        <a-menu-item key="2">linkhub</a-menu-item>
        <a-menu-item key="3">其他</a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout-content>
      <router-view></router-view>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router'; // 添加 useRoute

const selectedKeys1 = ref<string[]>(['1']);
const router = useRouter();
const route = useRoute(); // 获取当前路由

// 根据路由设置 selectedKeys1
const updateSelectedKeys = () => {
  switch (route.path) {
    case '/':
      selectedKeys1.value = ['1'];
      break;
    case '/linkhub':
      selectedKeys1.value = ['2'];
      break;
    case '/other':
      selectedKeys1.value = ['3'];
      break;
    default:
      selectedKeys1.value = ['1'];
  }
};

// 初始化时设置 selectedKeys1
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
      router.push('/other');
      break;
  }
};
</script>

<style scoped>
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