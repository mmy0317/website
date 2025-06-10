package website.project.website.service;

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
}
