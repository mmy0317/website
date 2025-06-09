package website.project.website.service;

import website.project.website.domain.dto.UserDTO;

public interface UserService {

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserDTO selectUserDtoByUserId(String userId);

}
