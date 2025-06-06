package website.project.website.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.project.website.domain.dto.UserDTO;
import website.project.website.interceptor.AuthNHolder;
import website.project.website.service.UserService;
import website.project.website.service.WebsiteQueryService;

/**
 * 管理员后台权限操作请求控制器
 * @author mayang
 */
@Slf4j
@RestController
@RequestMapping("/website/request")
public class WebsiteRequestController {

    @Resource
    private WebsiteQueryService websiteQueryService;

    @Resource
    private UserService userService;

    @GetMapping("/user")
    public UserDTO user(){
        String userId = AuthNHolder.get("userId");
        UserDTO userDTO = userService.selectUserDtoByUserId(userId);
        log.info("测试拦截器");
        return userDTO;
    }

}
