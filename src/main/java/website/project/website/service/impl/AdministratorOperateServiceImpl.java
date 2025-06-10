package website.project.website.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.AdministratorPageDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.entity.UserDO;
import website.project.website.enums.AdministratorStateEnum;
import website.project.website.enums.PermissionEnum;
import website.project.website.mapper.UserMapper;
import website.project.website.service.AdministratorOperateService;
import website.project.website.utils.AESUtil;
import website.project.website.utils.RSAUtil;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 用户密码正则校验: 至少8位，含大写字母和数字
     */
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    @Override
    public void addAdministratorUser(String name, String mobilePhone, String password) {
        //step1 根据手机号查询用户信息
        UserDO userDO = userMapper.selectByPhone(mobilePhone);
        if (Objects.nonNull(userDO)){
            throw new RuntimeException("当前手机号" + mobilePhone + "已存在用户:" + userDO.getName());
        }
        //step2 生成用户唯一ID todo @mayang 暂时先用手机号
        String userId = mobilePhone;
        //step3 密码格式正则校验
        String decryptPassword = decryptPasswordRSA(password);
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(decryptPassword);
        if (!matcher.matches()){
            throw new RuntimeException("密码过于简单, 至少8位，含大写字母和数字");
        }
        //step4 用户密码对称加密
        String secretPassword = encryptPasswordAES(decryptPassword);
        //step5 创建用户
        UserDO createUserDO = UserDO.builder().build();
        createUserDO.setUserId(userId);
        createUserDO.setName(name);
        createUserDO.setPassword(secretPassword);
        createUserDO.setPhone(mobilePhone);
        userMapper.insert(createUserDO);
        //step6 记录操作日志 todo @mayang
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void delAdministratorUser(String userId) {
        //step1 查询当前用户信息
        UserDO userDO = administratorUserCheck(userId);
        //step2 更新当前用户状态
        userDO.setState(AdministratorStateEnum.DELETE.getCode());
        userMapper.update(userDO,null);
        //step3 记录操作日志 todo @mayang
    }

    @Override
    public String getAdministratorUserPwd(String userId) {
        //step1 查询用户信息
        UserDO userDO = administratorUserCheck(userId);
        //step2 解密用户密码
        return decryptPasswordAES(userDO.getPassword());
    }

    @Override
    public Page<UserDTO> queryAdministratorUserPage(AdministratorPageDTO administratorPageDTO) {
        //step1 构建结果
        Page<UserDTO> userDTOPage = new Page<>(administratorPageDTO.getPage(), administratorPageDTO.getSize());
        //step2 分页查询
        Page<UserDO> page = new Page<>(administratorPageDTO.getPage(), administratorPageDTO.getSize());
        IPage<UserDO> userDOIPage = userMapper.selectAdministratorPage(page, administratorPageDTO);
        //step3 处理查询结果
        if (CollectionUtils.isEmpty(userDOIPage.getRecords())){
            return new Page<>(administratorPageDTO.getPage(), administratorPageDTO.getSize());
        }
        userDTOPage.setTotal(userDOIPage.getTotal());
        userDTOPage.setRecords(UserConvert.INSTANCE.userDoListToDtoList(userDOIPage.getRecords()));
        return userDTOPage;
    }

    private UserDO administratorUserCheck(String userId){
        UserDO userDO = userMapper.selectByUserId(userId);
        if (Objects.isNull(userDO)){
            throw new RuntimeException("用户:" + userId +" 不存在");
        }
        if (!PermissionEnum.ADMIN.name().equals(userDO.getPermission())){
            throw new RuntimeException("用户:" + userId +" 不是管理员,无法删除");
        }
        return userDO;
    }

    /**
     * 非对称加密-解密
     * @param secretPassword
     * @return
     */
    private String decryptPasswordRSA(String secretPassword){
        try {
            return RSAUtil.decrypt(secretPassword, RSA_PRIVATE_KEY);
        }catch (Exception e){
            log.error("用户信息解密失败");
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }

    /**
     * 对称加密-加密
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

    /**
     * 对称加密-解密
     * @param secretPassword
     * @return
     */
    private String decryptPasswordAES(String secretPassword){
        try {
            return AESUtil.decrypt(secretPassword, SECRET_KEY);
        }catch (Exception e){
            log.error("用户信息解密失败");
            throw new RuntimeException("网络繁忙, 请稍后重试");
        }
    }
}
