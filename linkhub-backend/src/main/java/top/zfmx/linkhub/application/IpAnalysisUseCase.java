package top.zfmx.linkhub.application;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.iplookup.service.IpLookupService;
import top.zfmx.linkhub.interfaces.dto.LookupResult;

/**
 * @author zfmx
 * @version 0.0.1
 **/
@Service
@RequiredArgsConstructor
public class IpAnalysisUseCase {

    @Resource
    private IpLookupService ipLookupService;  // 依赖抽象

    public LookupResult analyzeIp(String ip) {
        // 组合领域逻辑（如记录日志、关联业务规则）
        return ipLookupService.lookup(ip);
    }
}
