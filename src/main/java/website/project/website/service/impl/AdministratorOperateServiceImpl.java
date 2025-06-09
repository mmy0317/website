package website.project.website.service.impl;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import website.project.website.entity.UserDO;
import website.project.website.enums.AdministratorStateEnum;
import website.project.website.enums.PermissionEnum;
import website.project.website.mapper.UserMapper;
import website.project.website.service.AdministratorOperateService;
import website.project.website.utils.AESUtil;
import website.project.website.utils.RSAUtil;

import java.util.Objects;

@Slf4j
@Component
public class AdministratorOperateServiceImpl implements AdministratorOperateService {

    @Resource
    private UserMapper userMapper;

    /**
     * 非对称加密私钥
     */
    @Value("${password.rsa.privateKey}")
    private String RSA_PRIVATE_KEY;

    /**
     * 堆成加密密钥
     */
    @Value("${password.aes.secretKey}")
    private String SECRET_KEY;

    @Override
    public void addAdministratorUser(String name, String mobilePhone, String password) {
        //step1 根据手机号查询用户信息
        UserDO userDO = userMapper.selectByPhone(mobilePhone);
        if (Objects.nonNull(userDO)){
            throw new RuntimeException("当前手机号" + mobilePhone + "已存在用户:" + userDO.getName());
        }
        //step2 生成用户唯一ID todo @mayang 暂时先用手机号
        String userId = mobilePhone;
        //step3 用户密码对称加密
        String secretPassword = encryptPasswordAES(password);
        //step4 创建用户
        UserDO createUserDO = UserDO.builder().build();
        createUserDO.setUserId(userId);
        createUserDO.setName(name);
        createUserDO.setPassword(secretPassword);
        createUserDO.setPhone(mobilePhone);
        userMapper.insert(createUserDO);
        //step5 记录操作日志 todo @mayang
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void delAdministratorUser(String userId) {
        //step1 查询当前用户信息
        UserDO userDO = userMapper.selectByUserId(userId);
        if (Objects.isNull(userDO)){
            throw new RuntimeException("用户:" + userId +" 不存在");
        }
        if (!PermissionEnum.ADMIN.name().equals(userDO.getPermission())){
            throw new RuntimeException("用户:" + userId +" 不是管理员,无法删除");
        }
        //step2 更新当前用户状态
        userDO.setState(AdministratorStateEnum.DELETE.getCode());
        userMapper.update(userDO,null);
        //step3 记录操作日志 todo @mayang
    }

    private String decryptPasswordRSA(String password, String account){
        try {
            return RSAUtil.decrypt(password, RSA_PRIVATE_KEY);
        }catch (Exception e){
            log.error("用户注册, 私密信息解密失败, 注册account:{}", account);
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }

    /**
     * 对称加密
     * @param password
     * @return
     */
    private String encryptPasswordAES(String password){
        try {
            return AESUtil.encrypt(password, SECRET_KEY);
        }catch (Exception e){
            log.error("用户信息加密失败");
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }
}
