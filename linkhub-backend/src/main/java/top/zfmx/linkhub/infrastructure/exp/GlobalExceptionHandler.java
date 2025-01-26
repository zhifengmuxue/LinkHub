package top.zfmx.linkhub.infrastructure.exp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zfmx.linkhub.interfaces.dto.ApiResponse;

/**
 * 全局异常处理
 *
 * @author zfmx
 * @version 0.0.1
 **/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> exception(Exception e){
        log.error("全局异常处理：{}", e.getMessage());
        return ApiResponse.fail(9999, e.getMessage());
    }
}
