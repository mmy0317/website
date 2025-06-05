package website.project.website.domain.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 用户注册参数
 * @author mayang
 */
@Data
public class RegisterDTO {

    /**
     * 账号
     */
    @NotNull(value = "注册账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotNull(value = "注册密码不能为空")
    private String passCode;

    /**
     * 昵称
     */
    @NotNull(value = "昵称不能为空")
    private String nickName;

    /**
     * 性别
     */
    @NotNull(value = "性别不能为空 0-女 1-男 2-未知")
    private Integer gender;





}
