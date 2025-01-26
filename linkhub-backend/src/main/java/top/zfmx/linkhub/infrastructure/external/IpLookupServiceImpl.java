package top.zfmx.linkhub.infrastructure.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import top.zfmx.linkhub.domain.iplookup.service.IpLookupService;
import top.zfmx.linkhub.interfaces.dto.LookupResult;


import java.io.IOException;
import java.net.URI;
import java.util.Set;

/**
 * ip查询服务
 *
 * @author zfmx
 * @version 0.0.1
 **/
@Component
public class IpLookupServiceImpl implements IpLookupService {
    private static final Set<String> INVALID_IPS = Set.of("0.0.0.0", "127.0.0.1", "255.255.255.255");
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private static final String API_TEMPLATE = "http://ip-api.com/json/%s?fields=status,message,country,city,isp,org,as,query";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static class InvalidIpException extends RuntimeException {
        public InvalidIpException(String message) {
            super(message);
        }
    }

    @Override
    public LookupResult lookup(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("查询参数不能为空");
        }

        // 特殊IP地址过滤
        String[] invalidIPs = {"0.0.0.0", "127.0.0.1", "255.255.255.255"};
        for (String invalidIP : invalidIPs) {
            if (invalidIP.equals(query)) {
                throw new IllegalArgumentException("不支持查询特殊IP地址：" + query);
            }
        }

        // IP/域名格式验证
        String ipv4Pattern = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        String ipv6Pattern = "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$";
        String domainPattern = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.[A-Za-z]{2,})+$";

        if (!query.matches(ipv4Pattern) && !query.matches(ipv6Pattern) && !query.matches(domainPattern)) {
            throw new IllegalArgumentException("无效的查询参数：" + query);
        }

        try {
            URI uri = new URI(String.format(API_TEMPLATE, query));
            HttpGet request = new HttpGet(uri);

            return HTTP_CLIENT.execute(request, response -> {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    throw new IOException("API 请求失败，状态码: " + statusCode);
                }

                String json = EntityUtils.toString(response.getEntity());
                return parseResponse(json);
            });
        } catch (Exception e) {
            throw new RuntimeException("IP 查询失败: " + e.getMessage(), e);
        }
    }

    private LookupResult parseResponse(String json) throws JsonProcessingException {
        JsonNode root = OBJECT_MAPPER.readTree(json);

        if (!"success".equals(root.get("status").asText())) {
            throw new RuntimeException(root.get("message").asText());
        }

        return new LookupResult(
                root.get("query").asText(),
                root.get("isp").asText(),
                root.get("as").asText(),
                root.get("org").asText(),
                root.get("country").asText(),
                root.get("city").asText()
        );
    }
}