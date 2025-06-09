package website.project.website.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import website.project.website.enums.PermissionEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限拦截器
 * @author mayang
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //step1 获取当前线程用户权限
        Map<String, String> user = AuthNHolder.getMap();
        String permission = user.get("permission");
        //step2 获取当前用户请求路径
        String requestURI = request.getRequestURI();
        //step3 超级管理员权限
        if (requestURI.contains("/administrator/operate") && !PermissionEnum.ROOT.name().equals(permission)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"code\":403,\"message\":\"权限不足\"}");
            return false;
        }
        //step4 管理员权限 todo @mayang 暂时没有

        return true;
    }

}
