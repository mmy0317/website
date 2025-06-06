package website.project.website.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jdk.dynalink.beans.StaticClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.LoginParam;
import website.project.website.domain.param.RegisterParam;
import website.project.website.service.UserService;
import website.project.website.utils.AESUtil;
import website.project.website.utils.JwtUtil;
import website.project.website.utils.RSAUtil;
import website.project.website.utils.WebResponse;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录注册接口
 * @author mayang
 */
@Slf4j
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @Value("${password.aes.secretKey}")
    private String AES_SECRET_KEY;

    /**
     * 用户注册
     * @param registerParam
     * @return
     */
    @PostMapping("/register")
    public WebResponse<Void> register(@RequestBody RegisterParam registerParam){
        //todo 记得删除
        registerParam.setPassCode(aesEncryptTool(registerParam.getPassCode(),AES_SECRET_KEY));

        userService.register(UserConvert.INSTANCE.registerParam2RegisterDTO(registerParam));
        return WebResponse.success();
    }


    /**
     * 用户登录
     * @param loginParam
     * @param httpServletResponse
     * @return
     */
    @PostMapping("/login")
    public WebResponse<String> login(@RequestBody LoginParam loginParam, HttpServletResponse httpServletResponse){
        //todo 记得删除
        loginParam.setPassCode(aesEncryptTool(loginParam.getPassCode(),AES_SECRET_KEY));

        UserDTO userDTO = userService.login(UserConvert.INSTANCE.loginParam2LoginInfoDTO(loginParam));
        String token = JwtUtil.generateToken(userDTO.getUserId());
        httpServletResponse.addHeader("_security_token_",token);
        return WebResponse.success(token);
    }

    /**
     * 生成密钥工具方法
     * @return
     */
    @GetMapping("/rsaKey")
    public WebResponse<Map<String,String>> buildRsaKeyTool(){
        Map<String,String> keyMap = new HashMap<>();
        try{
            keyMap = RSAUtil.generateKeyPair();
            String key = AESUtil.getSecretKey();
            keyMap.put("aes",key);
        }catch (Exception e){
            log.error("生成RSA密钥对失败",e);
            return WebResponse.fail("生成RSA密钥对失败");
        }
        return WebResponse.success(keyMap);
    }

    private String aesEncryptTool(String password, String secretKey){
        try {
            String aesEncrypt = AESUtil.encrypt(password, secretKey);
            return aesEncrypt;
        }catch (Exception e){
            log.error("AES加密失败",e);
            return null;
        }
    }


}
