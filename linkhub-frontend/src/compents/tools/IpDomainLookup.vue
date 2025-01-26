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
    message.warning({
    content: '请输入要查询的IP地址或域名',
    duration: 3,
    });
    return;
}

const input = query.value.trim();

// 特殊IP地址过滤
const invalidIPs = ['0.0.0.0', '127.0.0.1', '255.255.255.255'];
if (invalidIPs.includes(input)) {
    message.error({
    content: '不支持查询以下特殊IP地址：\n' +
                '• 0.0.0.0 - 无效地址\n' +
                '• 127.0.0.1 - 本地回环地址\n' +
                '• 255.255.255.255 - 广播地址\n' +
                '这些地址具有特殊用途，无法提供有效信息',
    duration: 5,
    style: {
        whiteSpace: 'pre-line',
    },
    });
    return;
}

// IP/域名格式验证
const ipv4Pattern = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
const ipv6Pattern = /^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$/;
const domainPattern = /^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\.[A-Za-z]{2,})+$/;

if (!ipv4Pattern.test(input) && !ipv6Pattern.test(input) && !domainPattern.test(input)) {
    message.error({
    content: '请输入有效的IP地址或域名\n\n' +
                '有效格式示例：\n' +
                '• IPv4地址：192.168.1.1\n' +
                '• IPv6地址：2001:0db8:85a3:0000:0000:8a2e:0370:7334\n' +
                '• 域名：example.com',
    duration: 5,
    style: {
        whiteSpace: 'pre-line',
    },
    });
    return;
}

if (input.length > 253) {
    message.error('输入内容过长，请检查后重试');
    return;
}

isLoading.value = true;
try {
    const response = await axios.get('/api/lookup', {
    params: {
        query: encodeURIComponent(input),
    },
    });

    result.value = {
    ip: response.data.ip || response.data.query,
    isp: response.data.isp,
    asn: response.data.asn || response.data.as,
    org: response.data.org,
    country: response.data.country,
    city: response.data.city,
    };
} catch (error) {
    console.error('查询失败:', error);
    message.error({
    content: '查询失败，可能原因：\n' +
                '1. 网络连接异常\n' +
                '2. 输入内容无效\n' +
                '3. 服务暂时不可用\n' +
                '请稍后重试或检查输入内容',
    duration: 5,
    style: {
        whiteSpace: 'pre-line',
    },
    });
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