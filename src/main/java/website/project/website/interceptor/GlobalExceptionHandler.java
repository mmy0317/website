package website.project.website.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import website.project.website.utils.WebResponse;

/**
 * 全局异常处理
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public WebResponse<Void> handleException(Exception e) {
        log.error("全局异常捕获: ", e);
        return WebResponse.fail("系统异常: " + e.getMessage());
    }
}