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

    @TableField("password")
    private String password;

    @TableField("nick_name")
    private String nickName;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("gender")
    private String gender;

}
