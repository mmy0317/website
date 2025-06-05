package website.project.website.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import website.project.website.convert.UserConvert;
import website.project.website.domain.param.LoginParam;
import website.project.website.domain.param.RegisterParam;
import website.project.website.service.UserService;
import website.project.website.utils.JwtUtil;

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
    public void login(@RequestBody LoginParam loginParam){
        String token = JwtUtil.generateToken("1930468404150337538");
        log.info("用户登录信息:{},{}",loginParam.toString(),token);
    }


    @PostMapping("/register")
    public void register(@RequestBody RegisterParam registerParam){
        userService.register(UserConvert.INSTANCE.registerParam2RegisterDTO(registerParam));
        log.info("用户注册信息:{}",registerParam.toString());
    }


}
