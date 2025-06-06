package website.project.website.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("users")
public class UserDO {

    @TableId(value="user_id")
    private String userId;

    @TableField("account")
    private String account;

    @TableField("nick_name")
    private String nickName;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("gender")
    private String gender;

    /**
     * 用户权限
     * 0-ROOT 超级管理员
     * 1-ADMIN 管理员
     * 2-ORDINARY 普通用户
     */
    @TableField("permission")
    private String permission;

}
