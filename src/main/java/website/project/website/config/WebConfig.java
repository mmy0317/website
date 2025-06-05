package website.project.website.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import website.project.website.interceptor.WebInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor)
                .addPathPatterns("/**")          // 拦截所有路径
                .excludePathPatterns(            // 排除路径
                        "/login",
                        "/register",
                        "/rsaKey"
                ).order(1); // 拦截器执行顺序（值越小优先级越高）
    }
}
