package top.zfmx.linkhub.interfaces.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zfmx.linkhub.application.IpAnalysisUseCase;
import top.zfmx.linkhub.infrastructure.external.IpLookupServiceImpl;
import top.zfmx.linkhub.interfaces.dto.LookupResult;

/**
 * ip查询接口
 *
 * @author zfmx
 * @version 0.0.1
 **/

@RestController
public class IpLookupController {

    @Resource
    private IpAnalysisUseCase ipAnalysisUseCase;

    @GetMapping("/lookup")
    public LookupResult lookup(@RequestParam String query) {
        try {
            return ipAnalysisUseCase.analyzeIp(query);
        } catch (Exception e) {
            throw new RuntimeException("查询失败", e);
        }
    }
}
