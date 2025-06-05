package website.project.website.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.LoginParam;
import website.project.website.domain.param.RegisterParam;
import website.project.website.service.UserService;
import website.project.website.utils.JwtUtil;
import website.project.website.utils.WebResponse;

/**
 * 用户登录注册接口
 * @author mayang
 */
@Slf4j
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public WebResponse<String> login(@RequestBody LoginParam loginParam, HttpServletResponse httpServletResponse){
        UserDTO userDTO = userService.login(UserConvert.INSTANCE.loginParam2LoginInfoDTO(loginParam));
        String token = JwtUtil.generateToken(userDTO.getUserId());
        httpServletResponse.addHeader("_security_token_",token);
        return WebResponse.success(token);
    }


    @PostMapping("/register")
    public WebResponse<Void> register(@RequestBody RegisterParam registerParam){
        userService.register(UserConvert.INSTANCE.registerParam2RegisterDTO(registerParam));
        return WebResponse.success();
    }


}
