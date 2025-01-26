<template>
<div class="home-container" @wheel="handleWheel">
 
    <!-- 左侧标题和副标题区域 -->
    <div class="left-section">
    <div class="hero-section">
        <h1 class="title animate__animated animate__fadeInDown">LinkHub</h1>
        <p class="subtitle animate__animated animate__fadeInUp">高效工具，触手可及</p>
    </div>
    </div>

    <!-- 右侧描述文字区域 -->
    <div class="right-section animate__animated animate__fadeIn">
    <div class="description-section">
        <p class="description">
        LinkHub 是一个专注于提升开发效率的工具集合平台。<br />
        在这里，你可以快速找到前后端开发、文档查阅、工具推荐等资源，<br />
        让开发变得更加简单和高效。
        </p>
    </div>
    </div>

    <!-- 页脚 -->
    <div class="footer animate__animated animate__fadeInUp">
    <p>© 2025 LinkHub. All rights reserved.</p>
    <p>
        <a href="https://beian.miit.gov.cn/" target="_blank" rel="noopener noreferrer">
        浙ICP备2024065193号-2
        </a>
    </p>
    </div>

</div>
</template>

<script lang="ts" setup>
import 'animate.css'; // 引入 animate.css
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const isScrolling = ref(false);

const handleWheel = (event: WheelEvent) => {
  if (isScrolling.value) return;

  if (event.deltaY > 0) {
    isScrolling.value = true;
    
    window.scrollTo({
      top: document.body.scrollHeight,
      behavior: 'smooth'
    });

    setTimeout(() => {
      router.push('/linkhub');
      isScrolling.value = false;
    }, 800); 
  }
};

const handleKeyDown = (event: KeyboardEvent) => {
  if (event.key === 'ArrowDown' || event.key === 'PageDown') {
    router.push('/linkhub');
  }
};

onMounted(() => {
  window.addEventListener('wheel', handleWheel, { passive: true }); // 添加 { passive: true }
  window.addEventListener('keydown', handleKeyDown);
});

onUnmounted(() => {
  window.removeEventListener('wheel', handleWheel);
  window.removeEventListener('keydown', handleKeyDown);
});
</script>

<style scoped>
.home-container {
  display: flex;
  gap: 48px;
  width: 100vw;
  margin: 0 auto;
  padding: 100px 24px 80px;
  min-height: calc(100vh - 80px);
  align-items: center;
  justify-content: center;
  position: relative;
  background-color: #fff;
}

.left-section {
  flex: 1;
  max-width: 400px;
  text-align: center;
}

.hero-section {
  margin-bottom: 24px;
}

.title {
  font-size: 64px;
  font-weight: bold;
  background: linear-gradient(45deg, #001529, #1890ff);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  margin-bottom: 16px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}   

.subtitle {
  font-size: 24px;
  color: #666;
  font-weight: 500;
}

.right-section {
  flex: 1;
  max-width: 600px;
  text-align: center;
}

.description-section {
  margin-bottom: 24px;
}

.description {
  font-size: 18px;
  line-height: 1.8;
  color: #444;
}

.footer {
  text-align: center;
  padding: 24px;
  color: #666;
  font-size: 14px;
  opacity: 0.8;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: #fff;
}

.footer a {
  color: inherit;
  text-decoration: none;
}

.footer a:hover {
  text-decoration: underline;
}

/* 响应式布局 */
@media (max-width: 768px) {
  .home-container {
    flex-direction: column;
    gap: 24px;
    padding: 48px 24px;
  }

  .left-section,
  .right-section {
    max-width: 100%;
  }

  .title {
    font-size: 48px;
  }

  .subtitle {
    font-size: 20px;
  }

  .description {
    font-size: 16px;
  }
}
</style>