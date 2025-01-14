<template>
  <div class="tool-box">
    <a-row :gutter="24">
      <!-- 链接缩短 -->
      <a-col :span="8">
        <a-card class="tool-card">
          <template #title>
            <span class="tool-title">链接缩短</span>
          </template>
          <a-input v-model:value="longUrl" placeholder="请输入长链接" />
          <a-button
            type="primary"
            @click="shortenUrl"
            :loading="isShortening"
            style="margin-top: 16px;"
            block
          >
            生成短链接
          </a-button>
          <div v-if="shortUrl" class="tool-result">
            <p>短链接：</p>
            <a :href="shortUrl" target="_blank">{{ shortUrl }}</a>
          </div>
        </a-card>
      </a-col>

      <!-- 二维码生成 -->
      <a-col :span="8">
        <a-card class="tool-card">
          <template #title>
            <span class="tool-title">二维码生成</span>
          </template>
          <a-input v-model:value="qrContent" placeholder="请输入链接或文本" />
          <a-button
            type="primary"
            @click="generateQrCode"
            :loading="isGeneratingQr"
            style="margin-top: 16px;"
            block
          >
            生成二维码
          </a-button>
          <div v-if="qrCodeUrl" class="tool-result">
            <p>二维码：</p>
            <img :src="qrCodeUrl" alt="二维码" />
          </div>
        </a-card>
      </a-col>

      <!-- 批量处理 -->
      <a-col :span="8">
        <a-card class="tool-card">
          <template #title>
            <span class="tool-title">批量处理</span>
          </template>
          <a-textarea
            v-model:value="batchInput"
            placeholder="每行输入一个链接"
            :rows="4"
            style="margin-bottom: 16px;"
          />
          <a-radio-group v-model:value="batchAction" style="margin-bottom: 16px;">
            <a-radio value="shorten">批量缩短链接</a-radio>
            <a-radio value="qr">批量生成二维码</a-radio>
          </a-radio-group>
          <a-button
            type="primary"
            @click="batchProcess"
            :loading="isBatchProcessing"
            block
          >
            开始处理
          </a-button>
          <div v-if="batchResults.length > 0" class="tool-result">
            <p>处理结果：</p>
            <a-collapse>
              <a-collapse-panel
                v-for="(result, index) in batchResults"
                :key="index"
                :header="`链接 ${index + 1}`"
              >
                <div v-if="batchAction === 'shorten'">
                  <p>原链接：{{ result.original }}</p>
                  <p>
                    短链接：
                    <a :href="result.shortened" target="_blank">{{ result.shortened }}</a>
                  </p>
                </div>
                <div v-else>
                  <p>链接：{{ result.original }}</p>
                  <img :src="result.qrCode" alt="二维码" style="margin-top: 8px;" />
                </div>
              </a-collapse-panel>
            </a-collapse>
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

// 链接缩短
const longUrl = ref('');
const shortUrl = ref('');
const isShortening = ref(false);
const BASE_URL = 'http://localhost:8080/link/'
const shortenUrl = async () => {
  if (!longUrl.value) {
    message.warning('请输入长链接');
    return;
  }
  isShortening.value = true;
  try {
    const response = await axios.post('api/link/generate', {
      url: longUrl.value,
    }, {
      headers: {
        'Content-Type': 'application/json', // 正确设置请求头
      },
    });
    shortUrl.value = BASE_URL + response.data.shortCode;
    message.success('短链接生成成功');
  } catch (error) {
    console.error(error);
    message.error('生成短链接失败');
  } finally {
    isShortening.value = false;
  }
};

// 二维码生成
const qrContent = ref('');
const qrCodeUrl = ref('');
const isGeneratingQr = ref(false);

const generateQrCode = async () => {
  if (!qrContent.value) {
    message.warning('请输入内容');
    return;
  }
  isGeneratingQr.value = true;
  try {
    qrCodeUrl.value = `https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(qrContent.value)}`;
    message.success('二维码生成成功');
  } catch (error) {
    message.error('生成二维码失败');
  } finally {
    isGeneratingQr.value = false;
  }
};

// 批量处理
const batchInput = ref('');
const batchAction = ref<'shorten' | 'qr'>('shorten');
const isBatchProcessing = ref(false);
const batchResults = ref<{ original: string; shortened?: string; qrCode?: string }[]>([]);

const batchProcess = async () => {
  if (!batchInput.value) {
    message.warning('请输入链接');
    return;
  }

  isBatchProcessing.value = true;
  batchResults.value = [];

  const links = batchInput.value.split('\n').filter(link => link.trim());

  try {
    for (const link of links) {
      if (batchAction.value === 'shorten') {
        const shortened = `https://short.url/${Math.random().toString(36).slice(2, 8)}`;
        batchResults.value.push({ original: link, shortened });
      } else {
        const qrCode = `https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(link)}`;
        batchResults.value.push({ original: link, qrCode });
      }
    }
    message.success(`成功处理 ${links.length} 个链接`);
  } catch (error) {
    message.error('处理失败，请重试');
  } finally {
    isBatchProcessing.value = false;
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

.tool-result img {
  display: block;
  margin-top: 8px;
  max-width: 100%;
}
</style>