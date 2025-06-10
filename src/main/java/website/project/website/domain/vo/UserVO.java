package website.project.website.domain.vo;


import lombok.Data;
import website.project.website.enums.AdministratorStateEnum;
import website.project.website.enums.PermissionEnum;

@Data
public class UserVO {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 用户权限
     */
    private PermissionEnum permission;

    /**
     * 用户状态
     */
    private AdministratorStateEnum state;

}
