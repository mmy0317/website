package website.project.website.service;


import jakarta.servlet.http.HttpServletRequest;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.RegisterDTO;
import website.project.website.domain.dto.UserDTO;

public interface UserService {

    /**
     * 用户注册
     * @param registerDTO
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户登录
     * @param loginInfoDTO
     */
    UserDTO login(LoginInfoDTO loginInfoDTO);

    UserDTO selectUserDtoByUserId(String userId);

}
