package website.project.website.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.RegisterDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.entity.UserDO;
import website.project.website.mapper.UserMapper;
import website.project.website.service.UserService;

import java.util.Objects;

/**
 * 用户信息服务
 * @author mayang
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String checkToken(String token) {
        //step1 解析token获取失效时间,用户userCode
        String userCode = "1231213";

        return userCode;
    }


    @Override
    public void register(RegisterDTO registerDTO) {
        //step1 参数校验
        UserDO userDO = userMapper.selectUserDoByAccount(registerDTO.getAccount());
        if (Objects.nonNull(userDO)) {
            log.info("当前账号已注册");
            throw new RuntimeException("当前账号已注册");
        }
        //step2 插入用户信息
        UserDO registerUser = UserConvert.INSTANCE.registerDto2UserDO(registerDTO);
        userMapper.insert(registerUser);
    }

    @Override
    public UserDTO selectUserDtoByUserId(String userId) {
        UserDO userDO = userMapper.selectByUserId(userId);
        return UserConvert.INSTANCE.userDo2Dto(userDO);
    }
}
