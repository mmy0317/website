package website.project.website.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import website.project.website.domain.dto.UserDTO;
import website.project.website.service.UserService;
import website.project.website.utils.JwtUtil;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
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

    private final String TOKEN_CACHE_KEY = "TOKEN::CACHE::KEY";
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 请求拦截器(验证token)
     * todo 可以增加一个续费的逻辑, 时间不加在token上, 而是用redis存储
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        AuthNHolder.clear();
        //step1 token校验
        String token = httpServletRequest.getHeader("_security_token_");
        if (Objects.isNull(token)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("{\"code\":500,\"message\":\"token is null\"}");
            return false;
        }
        //step2 token校验
        Claims claims = JwtUtil.parseToken(token);
        if (claims.getExpiration().before(new Date())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("{\"code\":500,\"message\":\"token is expired\"}");
            return false;
        }
        //step3 用户信息查询
        String userId = claims.getSubject();
        UserDTO userDTO = userService.selectUserDtoByUserId(userId);
        if (Objects.isNull(userDTO)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("{\"code\":500,\"message\":\"user is null\"}");
            return false;
        }
        //step4 当前线程缓存用户信息
        AuthNHolder.set(objectToMap(userDTO));
        return true;
    }

    private static HashMap<String,String> objectToMap(Object o){
        HashMap<String,String> map = new HashMap<>();
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), String.valueOf(field.get(o)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

}
