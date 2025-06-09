package website.project.website.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.entity.UserDO;
import website.project.website.mapper.UserMapper;
import website.project.website.service.LoginService;
import website.project.website.utils.AESUtil;
import website.project.website.utils.RSAUtil;

import java.util.Objects;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${password.rsa.privateKey}")
    private String RSA_PRIVATE_KEY;

    @Value("${password.aes.secretKey}")
    private String AES_SECRET_KEY;

    private final String VERIFY_CODE_KEY = "VERIFY::CODE::KEY::";

    /**
     * 用户登录
     * @param loginInfoDTO
     * @return
     */
    @Override
    public UserDTO login(LoginInfoDTO loginInfoDTO) {
        //step1 用户信息查询
        UserDO userDO = userMapper.selectByPhone(loginInfoDTO.getPhone());
        if (Objects.isNull(userDO)){
            throw new RuntimeException("手机号:" + loginInfoDTO.getPhone() + "未注册");
        }
        //step2 优先使用手机验证码登录
        if (StringUtils.isNotBlank(loginInfoDTO.getVerificationCode())) {
            verifyCodeCheck(loginInfoDTO.getPhone(),loginInfoDTO.getVerificationCode());
            return UserConvert.INSTANCE.userDoToDto(userDO);
        }
        //step3 账号密码登录
        String decryptPasswordRsa = decryptPasswordRSA(loginInfoDTO.getPassWord());
        String decryptPasswordAes = decryptPasswordAES(userDO.getPassword());
        if (!Objects.equals(decryptPasswordAes,decryptPasswordRsa)){
            throw new RuntimeException("账户名或者密码错误");
        }
        return UserConvert.INSTANCE.userDoToDto(userDO);
    }



    /**
     * 短信验证码校验
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     */
    private void verifyCodeCheck(String phoneNumber, String verifyCode) {
        //todo @mayang 目前先不校验手机短信验证码
//        String value = stringRedisTemplate.opsForValue().get(VERIFY_CODE_KEY + phoneNumber);
//        if (StringUtils.isBlank(value)) {
//            throw new RuntimeException("短信验证码已过期, 请重新发送");
//        }
//        if (Objects.equals(value, verifyCode)) {
//            throw new RuntimeException("短信验证码错误, 请重新输入");
//        }
//        stringRedisTemplate.delete(VERIFY_CODE_KEY + phoneNumber);
    }

    /**
     * 非对称加密解密
     * @param password
     * @return
     */
    private String decryptPasswordRSA(String password){
        try {
            return RSAUtil.decrypt(password, RSA_PRIVATE_KEY);
        }catch (Exception e){
            log.error("用户密码解密失败");
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }

    /**
     * 对称加密解密
     * @param password
     * @return
     */
    private String decryptPasswordAES(String password){
        try {
            return AESUtil.decrypt(password, AES_SECRET_KEY);
        }catch (Exception e){
            log.error("用户密码解密失败");
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }
}
