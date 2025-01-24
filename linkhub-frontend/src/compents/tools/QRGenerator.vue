<template>
  <div class="tool-box">
    <a-row :gutter="24">
      <a-col :span="24">
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
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { message } from 'ant-design-vue';

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