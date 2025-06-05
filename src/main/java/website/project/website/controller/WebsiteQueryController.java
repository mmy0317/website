package website.project.website.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import website.project.website.domain.dto.UserDTO;
import website.project.website.service.UserService;
import website.project.website.service.WebsiteQueryService;
import website.project.website.utils.AuthNHolder;

/**
 * 网页请求
 * @author mayang
 */
@Slf4j
@RestController
@RequestMapping("/website/query")
public class WebsiteQueryController {

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
