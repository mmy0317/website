package website.project.website.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import website.project.website.convert.CommonConvert;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.LoginParam;
import website.project.website.service.LoginService;
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
    private LoginService loginService;

    /**
     * 用户登录
     * @param loginParam
     * @param httpServletResponse
     * @return
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginParam loginParam, HttpServletResponse httpServletResponse){
        LoginInfoDTO loginInfoDTO = CommonConvert.INSTANCE.loginParamToDTO(loginParam);
        UserDTO userDTO = loginService.login(loginInfoDTO);
        httpServletResponse.setHeader("_security_token_",JwtUtil.generateToken(userDTO.getUserId()));
    }

    @GetMapping("/test")
    public WebResponse<String> test(){
        return WebResponse.success("成功");
    }


    @GetMapping("/test/getRoot")
    public WebResponse<String> getRoot(){
        return WebResponse.success(JwtUtil.generateToken("00001"));
    }


}
