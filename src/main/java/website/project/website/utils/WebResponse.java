package website.project.website.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

/**
 * 返回值统一处理类
 * @param <T>
 */
@Getter
@AllArgsConstructor
public class WebResponse<T> {

    /**
     * 返回值code
     */
    private Integer code;

    /**
     * 返回值文案
     */
    private String message;

    /**
     * 返回值对象
     */
    private T data;

    /**
     * 处理时间
     */
    private LocalDateTime responseTime;


    public static <T> WebResponse<T> success(T data) {
        return new WebResponse<>(200, "success", data, LocalDateTime.now());
    }

    public static <T> WebResponse<T> success() {
        return new WebResponse<>(200, "success", null, LocalDateTime.now());
    }

    public static <T> WebResponse<T> fail(T data, String message) {
        return new WebResponse<>(500, message, data, LocalDateTime.now());
    }

    public static <T> WebResponse<T> fail(T data, String message, Integer code) {
        return new WebResponse<>(code, message, data, LocalDateTime.now());
    }

}
