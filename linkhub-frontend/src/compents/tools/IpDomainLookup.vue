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

const query = ref('');
const result = ref<LookupResult | null>(null);
const isLoading = ref(false);

const lookup = async () => {
if (!query.value) {
    message.warning('请输入IP地址或域名');
    return;
}

isLoading.value = true;
try {
    const response = await axios.get(`api/lookup?query=${query.value}`);
    result.value = {
    ip: response.data.ip,
    isp: response.data.isp,
    asn: response.data.asn,
    org: response.data.org,
    country: response.data.country,
    city: response.data.city
    };
} catch (error) {
    console.error(error);
    message.error('查询失败');
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