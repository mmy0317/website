package website.project.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import website.project.website.domain.dto.AdministratorPageDTO;
import website.project.website.domain.dto.UserDTO;

import java.util.List;

public interface AdministratorOperateService {

    /**
     * 增加管理员用户
     * @param name 用户姓名
     * @param mobilePhone 手机号
     * @param password 密码
     * @return
     */
    void addAdministratorUser(String name, String mobilePhone, String password);

    /**
     * 删除管理员用户
     * @param userId
     */
    void delAdministratorUser(String userId);

    /**
     * 查询管理员用户账户密码
     * @param userId
     * @return
     */
    String getAdministratorUserPwd(String userId);

    /**
     * 管理员用户分页查询
     * @param administratorPageDTO
     * @return
     */
    Page<UserDTO> queryAdministratorUserPage(AdministratorPageDTO administratorPageDTO);
}
