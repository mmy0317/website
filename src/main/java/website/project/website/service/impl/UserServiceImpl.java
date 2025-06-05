package website.project.website.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.RegisterDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.entity.UserDO;
import website.project.website.mapper.UserMapper;
import website.project.website.service.UserService;
import website.project.website.utils.AESUtil;
import website.project.website.utils.RSAUtil;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户信息服务
 * @author mayang
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Value("${password.rsa.privateKey}")
    private String RSA_PRIVATE_KEY;

    @Value("${password.aes.secretKey}")
    private String SECRET_KEY;

    /**
     * 用户密码正则校验: 至少8位，含大写字母和数字
     */
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void register(RegisterDTO registerDTO) {
        //step1 参数校验
        UserDO userDO = userMapper.selectUserDoByAccount(registerDTO.getAccount());
        if (Objects.nonNull(userDO)) {
            log.info("当前账号已注册");
            throw new RuntimeException("当前账号已注册");
        }
        //step2 解密用户信息
        String pwd = decryptPasswordRSA(registerDTO.getPassCode(),registerDTO.getAccount());
        //step3 密码信息正则校验
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pwd);
        if (!matcher.matches()){
            throw new RuntimeException("密码过于简单, 至少8位，含大写字母和数字");
        }
        //step4 密码加密存储
        registerDTO.setPassCode(encryptPasswordAES(pwd,registerDTO.getAccount()));
        //step5 数据存储
        UserDO registerUser = UserConvert.INSTANCE.registerDto2UserDO(registerDTO);
        userMapper.insert(registerUser);
    }

    @Override
    public UserDTO login(LoginInfoDTO loginInfoDTO) {
        //step1 用户密码解密
        String decryptPassword = decryptPasswordRSA(loginInfoDTO.getPassCode(),loginInfoDTO.getAccount());
        //step2 用户密码加密
        String encryptPassword = encryptPasswordAES(decryptPassword,loginInfoDTO.getAccount());
        UserDO userDO = userMapper.selectUserDoByAccountAndPassword(loginInfoDTO.getAccount(),encryptPassword);
        if (Objects.isNull(userDO)) {
            log.info("账号或密码错误");
            throw new RuntimeException("账号或密码错误");
        }
        return UserConvert.INSTANCE.userDo2Dto(userDO);
    }

    private String decryptPasswordRSA(String password, String account){
        try {
            return RSAUtil.decrypt(password, RSA_PRIVATE_KEY);
        }catch (Exception e){
            log.error("用户注册, 私密信息解密失败, 注册account:{}", account);
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }

    private String encryptPasswordAES(String password, String account){
        try {
            return AESUtil.encrypt(password, SECRET_KEY);
        }catch (Exception e){
            log.error("用户注册, 私密信息加密失败, 注册account:{}", account);
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }

    @Override
    public UserDTO selectUserDtoByUserId(String userId) {
        UserDO userDO = userMapper.selectByUserId(userId);
        return UserConvert.INSTANCE.userDo2Dto(userDO);
    }
}
