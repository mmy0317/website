package website.project.website.service;


import jakarta.servlet.http.HttpServletRequest;
import website.project.website.domain.dto.RegisterDTO;
import website.project.website.domain.dto.UserDTO;

public interface UserService {

    /**
     * 用户token校验
     * @param token
     * @return
     */
    String checkToken(String token);

    /**
     * 用户注册
     * @param registerDTO
     */
    void register(RegisterDTO registerDTO);

    UserDTO selectUserDtoByUserId(String userId);

}
