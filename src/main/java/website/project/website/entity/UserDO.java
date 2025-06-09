package website.project.website.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import website.project.website.enums.PermissionEnum;

@Data
@Builder
@TableName("users")
public class UserDO {

    @TableId(value="user_id")
    private String userId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 用户性别 0-女 1-男 2-未知
     */
    @TableField("gender")
    private String gender;

    /**
     * 用户权限
     * 0-ROOT 超级管理员
     * 1-ADMIN 管理员
     * 2-ORDINARY 普通用户
     * @see PermissionEnum
     */
    @TableField("permission")
    private String permission;

    /**
     * 用户状态 0-已删除 1-使用中
     */
    @TableField("state")
    private Integer state;


    /**
     * CREATE TABLE `users` (
     *   `user_id` varchar(32) NOT NULL COMMENT '用户id',
     *   `name` varchar(64) DEFAULT NULL COMMENT '用户姓名',
     *   `nick_name` varchar(32) NOT NULL COMMENT '昵称',
     *   `password` varchar(255) DEFAULT NULL COMMENT '密码',
     *   `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
     *   `phone` varchar(255) NOT NULL COMMENT '用户手机号',
     *   `gender` tinyint(1) NOT NULL DEFAULT '2' COMMENT '用户性别 0-女 1-男 2-未知',
     *   `permission` varchar(64) NOT NULL DEFAULT 'ORDINARY' COMMENT '用户权限',
     *   `state` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态 0-已删除 1-使用中'
     *   PRIMARY KEY (`user_id`) USING BTREE
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

}
