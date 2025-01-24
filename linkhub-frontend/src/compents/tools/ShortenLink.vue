<template>
  <div class="tool-box">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-card class="tool-card">
          <template #title>
            <span class="tool-title">短链接生成</span>
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
    </a-row>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { message } from 'ant-design-vue';
import axios from 'axios';
import { config } from '@/config';

const longUrl = ref('');
const shortUrl = ref('');
const isShortening = ref(false);

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
        'Content-Type': 'application/json',
      },
    });
    shortUrl.value = config.BASE_URL + response.data.shortCode;
    message.success('短链接生成成功');
  } catch (error) {
    console.error(error);
    message.error('生成短链接失败');
  } finally {
    isShortening.value = false;
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

.tool-result a {
  word-break: break-all;
}
</style>