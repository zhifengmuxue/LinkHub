<template>
<div class="tool-box">
    <a-row :gutter="24">
    <a-col :span="24">
        <a-card class="tool-card">
        <template #title>
            <span class="tool-title">IP/域名查询</span>
        </template>
        <a-input v-model:value="query" placeholder="请输入IP地址或域名" />
        <a-button
            type="primary"
            @click="lookup"
            :loading="isLoading"
            style="margin-top: 16px;"
            block
        >
            查询
        </a-button>
        <div v-if="result" class="tool-result">
            <a-descriptions bordered :column="1">
            <a-descriptions-item label="IP地址">{{ result.ip }}</a-descriptions-item>
            <a-descriptions-item label="ISP">{{ result.isp }}</a-descriptions-item>
            <a-descriptions-item label="ASN">{{ result.asn }}</a-descriptions-item>
            <a-descriptions-item label="ASN组织">{{ result.org }}</a-descriptions-item>
            <a-descriptions-item label="国家">{{ result.country }}</a-descriptions-item>
            <a-descriptions-item label="城市">{{ result.city }}</a-descriptions-item>
            </a-descriptions>
        </div>
        </a-card>
    </a-col>
    </a-row>
</div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { message } from 'ant-design-vue';
import axios from 'axios';

interface LookupResult {
  ip: string;
  isp: string;
  asn: string;
  org: string;
  country: string;
  city: string;
}

interface ApiResponse {
  code: number;
  message: string;
  resultData: LookupResult | null;
  timestamp: number;
}

const query = ref('');
const result = ref<LookupResult | null>(null);
const isLoading = ref(false);
const lookup = async () => {
  if (!query.value.trim()) {
    message.warning('请输入要查询的IP地址或域名');
    return;
  }

  isLoading.value = true;
  try {
    const response = await axios.get<ApiResponse>('/api/lookup', {
      params: { query: query.value.trim() }
    });

    console.log('API 响应:', response.data);

    if (response.data.code === 200 && response.data.resultData) {
      // 逐个字段赋值，确保 Vue 响应式系统正确追踪
      result.value = {
        ip: response.data.resultData.ip || '-',
        isp: response.data.resultData.isp || '-',
        asn: response.data.resultData.asn || '-',
        org: response.data.resultData.org || '-',
        country: response.data.resultData.country || '-',
        city: response.data.resultData.city || '-',
      };
      console.log('解析后的结果:', result.value);
    } else {
      message.error(response.data.message || '未知错误');
    }
  } catch (error) {
    console.error('请求失败:', error);
    if (axios.isAxiosError(error)) {
      const errorMessage = error.response?.data?.message || error.message;
      message.error(`查询失败: ${errorMessage}`);
    } else {
      message.error('网络请求异常');
    }
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.tool-box {
padding: 24px;
}

.tool-card {
height: 100%;
}

.tool-result {
margin-top: 16px;
}
</style>