//package website.project.website.service.impl;
//
//import io.micrometer.common.util.StringUtils;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import website.project.website.convert.UserConvert;
//import website.project.website.domain.dto.LoginInfoDTO;
//import website.project.website.domain.dto.RegisterDTO;
//import website.project.website.domain.dto.UserDTO;
//import website.project.website.entity.UserDO;
//import website.project.website.mapper.UserMapper;
//import website.project.website.service.UserService;
//import website.project.website.utils.AESUtil;
//import website.project.website.utils.RSAUtil;
//
//import java.security.SecureRandom;
//import java.util.Objects;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 用户信息服务
// * @author mayang
// */
//@Service
//@Slf4j
//public class UserServiceImpl implements UserService {
//
//    @Resource
//    private UserMapper userMapper;
//
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Value("${password.rsa.privateKey}")
//    private String RSA_PRIVATE_KEY;
//
//    @Value("${password.aes.secretKey}")
//    private String SECRET_KEY;
//
//    private final String VERIFY_CODE_KEY = "VERIFY::CODE::KEY::";
//
//    /**
//     * 用户密码正则校验: 至少8位，含大写字母和数字
//     */
//    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
//
//    @Override
//    @Transactional(rollbackFor=Exception.class)
//    public void register(RegisterDTO registerDTO) {
//        //step1 手机短信验证码校验
//        verifyCodeCheck(registerDTO.getPhone(),registerDTO.getVerifyCode());
//        //step2 参数校验
//        UserDO userDO = userMapper.selectUserDoByAccount(registerDTO.getAccount());
//        if (Objects.nonNull(userDO)) {
//            log.info("当前账号已注册");
//            throw new RuntimeException("当前账号已注册");
//        }
//        //step3 用户密码处理解加密
//        String pwd = decryptPasswordRSA(registerDTO.getPassCode(),registerDTO.getAccount());
//        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
//        Matcher matcher = pattern.matcher(pwd);
//        if (!matcher.matches()){
//            throw new RuntimeException("密码过于简单, 至少8位，含大写字母和数字");
//        }
//        registerDTO.setPassCode(encryptPasswordAES(pwd,registerDTO.getAccount()));
//        //step4 生成用户唯一ID todo @mayang
//
//        //step6 数据存储
//        UserDO registerUser = UserConvert.INSTANCE.registerDto2UserDO(registerDTO);
//        userMapper.insert(registerUser);
//    }
//
//    @Override
//    public UserDTO login(LoginInfoDTO loginInfoDTO) {
//        //step1 用户密码解密
//        String decryptPassword = decryptPasswordRSA(loginInfoDTO.getPassCode(),loginInfoDTO.getAccount());
//        //step2 用户密码加密
//        String encryptPassword = encryptPasswordAES(decryptPassword,loginInfoDTO.getAccount());
//        UserDO userDO = userMapper.selectUserDoByAccountAndPassword(loginInfoDTO.getAccount(),encryptPassword);
//        if (Objects.isNull(userDO)) {
//            log.info("账号或密码错误");
//            throw new RuntimeException("账号或密码错误");
//        }
//        return UserConvert.INSTANCE.userDo2Dto(userDO);
//    }
//
//    /**
//     * 短信验证码校验
//     * @param phoneNumber 手机号
//     * @param verifyCode 验证码
//     */
//    private void verifyCodeCheck(String phoneNumber, String verifyCode){
//        String value = stringRedisTemplate.opsForValue().get(VERIFY_CODE_KEY + phoneNumber);
//        if (StringUtils.isBlank(value)){
//            throw new RuntimeException("短信验证码已过期, 请重新发送");
//        }
//        if (Objects.equals(value,verifyCode)){
//            throw new RuntimeException("短信验证码错误, 请重新输入");
//        }
//        stringRedisTemplate.delete(VERIFY_CODE_KEY + phoneNumber);
//    }
//
//    private String decryptPasswordRSA(String password, String account){
//        try {
//            return RSAUtil.decrypt(password, RSA_PRIVATE_KEY);
//        }catch (Exception e){
//            log.error("用户注册, 私密信息解密失败, 注册account:{}", account);
//            throw new RuntimeException("网络繁忙, 请稍后重试");
//        }
//    }
//
//    private String encryptPasswordAES(String password, String account){
//        try {
//            return AESUtil.encrypt(password, SECRET_KEY);
//        }catch (Exception e){
//            log.error("用户注册, 私密信息加密失败, 注册account:{}", account);
//            throw new RuntimeException("网络繁忙, 请稍后重试");
//        }
//    }
//
//    @Override
//    public UserDTO selectUserDtoByUserId(String userId) {
//        UserDO userDO = userMapper.selectByUserId(userId);
//        return UserConvert.INSTANCE.userDo2Dto(userDO);
//    }
//}
