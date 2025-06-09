package website.project.website.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import website.project.website.enums.AdministratorStateEnum;
import website.project.website.enums.PermissionEnum;

@Data
@Builder
public class UserDTO {

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
