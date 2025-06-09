package website.project.website.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.UserDTO;
import website.project.website.entity.UserDO;
import website.project.website.mapper.UserMapper;
import website.project.website.service.UserService;

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
    public UserDTO selectUserDtoByUserId(String userId) {
        UserDO userDO = userMapper.selectByUserId(userId);
        return UserConvert.INSTANCE.userDoToDto(userDO);
    }
}
