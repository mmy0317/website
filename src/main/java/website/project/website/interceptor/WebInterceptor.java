package website.project.website.interceptor;

import com.alibaba.fastjson2.JSONObject;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.service.UserService;
import website.project.website.utils.JwtUtil;

import java.util.Objects;

/**
 * 请求拦截器
 * @author mayang
 */
@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;


    private final PathMatcher matcher = new AntPathMatcher();

    private final String includePatterns = "/basic/user/**";

    private final String TOKEN_CACHE_KEY = "TOKEN::CACHE::KEY";

    /**
     * 请求拦截器(验证token)
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //step1 token校验
        String token = httpServletRequest.getHeader("_security_token_");
        if (Objects.isNull(token)) {
            log.error("token is null");
            return false;
        }
        Claims claims = JwtUtil.parseToken(token);
        //step2 查询用户信息, 计入缓存 todo
        UserDTO userDTO = userService.selectUserDtoByUserId(claims.getSubject());
        return true;
    }

}
