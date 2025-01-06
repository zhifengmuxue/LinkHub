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
import { ref } from 'vue';
import { useRouter } from 'vue-router'; 

const selectedKeys1 = ref<string[]>(['1']);
const router = useRouter(); 

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