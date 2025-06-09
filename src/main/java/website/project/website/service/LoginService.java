package website.project.website.service;

import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.UserDTO;

/**
 * 用户登录
 * @author mayang
 */
public interface LoginService {

    /**
     * 用户登录
     * @param loginInfoDTO
     * @return token
     */
    UserDTO login(LoginInfoDTO loginInfoDTO);
}
