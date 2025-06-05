package website.project.website.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/login")
    public WebResponse<String> login(@RequestBody LoginParam loginParam, HttpServletResponse httpServletResponse){
        UserDTO userDTO = userService.login(UserConvert.INSTANCE.loginParam2LoginInfoDTO(loginParam));
        String token = JwtUtil.generateToken(userDTO.getUserId());
        httpServletResponse.addHeader("_security_token_",token);
        return WebResponse.success(token);
    }


    //RSA前端公钥加密, 后端私钥解密, 正则表达式校验密码格式, 数据针对密码加盐加密存储, 任何密码校验的明文处理都不应该在日志中展示
    @PostMapping("/register")
    public WebResponse<Void> register(@RequestBody RegisterParam registerParam){
        userService.register(UserConvert.INSTANCE.registerParam2RegisterDTO(registerParam));
        return WebResponse.success();
    }

    //设置管理员账号, 严格掌控账号权限

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

    /**
     * 测试加密密码方法(因为现在没有前端)
     * @param password
     * @return
     */
    @GetMapping("/aesEncrypt")
    public WebResponse<String> aesEncryptTool(@RequestParam("password") String password){
        try {
            String aesEncrypt = AESUtil.encrypt(password, "/Xck9WLbCkQQ7zx6UjPZ7Q==");
            return WebResponse.success(aesEncrypt);
        }catch (Exception e){
            log.error("AES加密失败",e);
            return WebResponse.fail("AES加密失败");
        }
    }


}
