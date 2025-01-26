package top.zfmx.linkhub.interfaces.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zfmx.linkhub.application.IpAnalysisUseCase;
import top.zfmx.linkhub.infrastructure.external.IpLookupServiceImpl;
import top.zfmx.linkhub.interfaces.dto.ApiResponse;
import top.zfmx.linkhub.interfaces.dto.LookupResult;

import java.util.NoSuchElementException;

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
    public ApiResponse<LookupResult> lookup(@RequestParam String query) {
        try {
            return ApiResponse.success(ipAnalysisUseCase.analyzeIp(query));
        } catch (IllegalArgumentException e) {
            // 输入参数错误（如无效IP或域名）
            return ApiResponse.fail(400, "输入内容无效");
        } catch (NoSuchElementException e) {
            // 未找到相关信息
            return ApiResponse.fail(404, "未找到相关信息");
        } catch (Exception e) {
            // 其他未知错误
            return ApiResponse.fail(500, "请检查输入内容");
        }
    }

//    @GetMapping("/lookup")
//    public ResponseEntity<?> lookup(@RequestParam String query) {
//        try {
//            LookupResult result = ipAnalysisUseCase.analyzeIp(query);
//            return ResponseEntity.ok(result);
//        } catch (IllegalArgumentException e) {
//            // 输入参数错误（如无效IP或域名）
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new ErrorResponse(400, "输入内容无效", e.getMessage()));
//        } catch (NoSuchElementException e) {
//            // 未找到相关信息
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorResponse(404, "未找到相关信息", e.getMessage()));
//        } catch (Exception e) {
//            // 其他未知错误
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse(500, "服务器内部错误", e.getMessage()));
//        }
//    }
}
