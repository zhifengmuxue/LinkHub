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
            <span v-if="!collapsed">全部工具</span>
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

      <!-- 内容区域 -->
      <a-layout style="padding: 0;">
        <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: 'calc(100vh - 64px - 24px)' }"
        >
          <div class="tool-container">
            <div class="tool-grid">
              <a-card 
                v-for="tool in filteredTools" 
                :key="tool.name"
                class="tool-card"
                hoverable
                @click="showToolModal(tool)"
              >
                <div class="tool-info">
                  <div class="tool-name">{{ tool.name }}</div>
                  <div class="tool-description">{{ tool.description }}</div>
                </div>
              </a-card>
            </div>
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>

    <!-- 工具模态框 -->
    <a-modal
      v-model:open="modalVisible"
      width="50%"
      :footer="null"
      :bodyStyle="{ padding: '0' }"
      :maskStyle="{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }"
      :wrapClassName="'custom-modal'"
    >
      <component :is="selectedTool?.component" v-if="modalVisible" />
    </a-modal>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue';
import { 
  HomeOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined
} from '@ant-design/icons-vue';
import ShortenLink from '@/compents/tools/ShortenLink.vue'; 
import QRGenerator from '@/compents/tools/QRGenerator.vue'; 
import IpDomainLookup from '@/compents/tools/IpDomainLookup.vue';
import { markRaw } from 'vue';


interface Tool {
  name: string;
  description: string;
  component: any;
}


const tools = ref<Tool[]>([
  {
    name: '短链接生成',
    description: '将长链接转换为短链接',
    component: markRaw(ShortenLink), // 使用 markRaw
  },
  {
    name: '二维码生成',
    description: '生成任意内容的二维码',
    component: markRaw(QRGenerator), // 使用 markRaw
  },
  {
    name: 'IP/域名查询',
    description: '查询IP地址或域名的详细信息',
    component: markRaw(IpDomainLookup), // 使用 markRaw
  }
]);


const selectedKeys = ref<string[]>(['0']);
const collapsed = ref(true);
const modalVisible = ref(false);
const selectedTool = ref<Tool | null>(null);

const filteredTools = computed(() => tools.value);

const toggleCollapsed = () => {
  collapsed.value = !collapsed.value;
};

const handleMenuClick = ({ key }: { key: string }) => {
  if (key === 'btn') return;
  selectedKeys.value = [key];
};

const showToolModal = (tool: Tool) => {
  selectedTool.value = tool;
  modalVisible.value = true;
};
</script>

<style scoped>
.tool-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); /* 调整卡片最小宽度 */
  gap: 12px; /* 调整间距 */
  padding: 12px; /* 调整内边距 */
}

.tool-card {
  cursor: pointer;
  transition: transform 0.2s; /* 保留悬停动画 */
  border-radius: 8px; /* 调整圆角 */
  overflow: hidden;
  background: #fff;
  border: 1px solid #e5e7eb;
  min-height: 120px; /* 调整最小高度 */
}

.tool-card:hover {
  transform: translateY(-2px); /* 调整悬停效果 */
}

.tool-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 12px; /* 调整内边距 */
  text-align: left;
  height: 100%;
}

.tool-name {
  font-size: 15px; /* 调整字体大小 */
  font-weight: bold;
  color: #111827;
  margin-bottom: 6px; /* 调整间距 */
}

.tool-description {
  font-size: 13px; /* 调整字体大小 */
  color: #6b7280;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-grow: 1;
}
</style>