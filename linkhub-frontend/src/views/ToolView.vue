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
          <a-menu-item key="1">
            <template #icon>
              <LinkOutlined />
            </template>
            <span v-if="!collapsed">链接工具</span>
          </a-menu-item>
          <a-menu-item key="2">
            <template #icon>
              <QrcodeOutlined />
            </template>
            <span v-if="!collapsed">二维码工具</span>
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
      v-model:visible="modalVisible"
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
  LinkOutlined,
  QrcodeOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined
} from '@ant-design/icons-vue';
import ShortenLink from '@/compents/tools/ShortenLink.vue'; 
import QRGenerator from '@/compents/tools/QRGenerator.vue'; 


interface Tool {
  name: string;
  description: string;
  component: any;
  category: string;
}

// 定义两个独立的工具
const shortenTool = ref<Tool>({
  name: '短链接生成',
  description: '将长链接转换为短链接',
  component: ShortenLink,
  category: 'link'
});

const qrTool = ref<Tool>({
  name: '二维码生成',
  description: '生成任意内容的二维码',
  component: QRGenerator,
  category: 'qr'
});

const tools = ref<Tool[]>([
  shortenTool.value,
  qrTool.value
]);

const categoryMap = {
  '0': '',       // 全部
  '1': 'link',   // 链接工具
  '2': 'qr'      // 二维码工具
};

const selectedKeys = ref<string[]>(['0']);
const collapsed = ref(true);
const modalVisible = ref(false);
const selectedTool = ref<Tool | null>(null);

const filteredTools = computed(() => {
  const category = categoryMap[selectedKeys.value[0] as keyof typeof categoryMap];
  return category ? tools.value.filter(tool => tool.category === category) : tools.value;
});

const toggleCollapsed = () => {
  collapsed.value = !collapsed.value;
};

const handleMenuClick = ({ key }: { key: keyof typeof categoryMap }) => {
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
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 16px; /* 缩小整体间距 */
  padding: 16px; /* 缩小容器内边距 */
}


.tool-card {
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1); /* 使用相同的动画曲线 */
  border-radius: 12px; /* 调整圆角 */
  overflow: hidden;
  background: #fff;
  border: 1px solid #e5e7eb;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  min-height: 140px; /* 调整卡片高度 */
  transition: transform 0.15s cubic-bezier(0.4, 0, 0.2, 1), 
              box-shadow 0.15s cubic-bezier(0.4, 0, 0.2, 1),
              border-color 0.15s cubic-bezier(0.4, 0, 0.2, 1);

}

.tool-card:hover {
  transform: translateY(-4px); /* 调整上移距离 */
  border-color: rgba(13, 13, 13, 0.5); /* 调整边框颜色透明度 */
  box-shadow: 0 10px 15px -3px rgba(59, 130, 246, 0.1), 
              0 4px 6px -2px rgba(59, 130, 246, 0.05); /* 调整阴影强度 */

}

.tool-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 16px; /* 调整内边距 */
  text-align: left;
  height: 100%;
}

.tool-name {
  font-size: 16px; /* 调整文字大小 */
  font-weight: 500;
  color: #111827;
  margin-bottom: 8px; /* 调整间距 */
}

.tool-description {
  font-size: 14px; /* 调整文字大小 */
  color: #6b7280;
  line-height: 1.6; /* 调整行高 */
  margin-bottom: 12px; /* 调整间距 */
  flex-grow: 1;
}

.tool-actions {
  width: 100%;
}

.tool-actions .ant-btn {
  width: 100%;
  border-radius: 8px; /* 调整按钮圆角 */
  height: 36px; /* 调整按钮高度 */
  font-size: 14px; /* 调整按钮文字大小 */
  padding: 0 16px; /* 调整按钮内边距 */
  background-color: #3b82f6;
  color: white;
  border: none;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
}

.tool-actions .ant-btn:hover {
  background-color: #2563eb;
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(59, 130, 246, 0.2);
}
</style>