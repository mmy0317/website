package website.project.website.domain.param;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * 用户登录请求参数
 * @author mayang
 */
@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @NotNull("登录手机号不能为空")
    private String phone;

    /**
     * 密码
     */
    private String passCode;

    /**
     * 手机短信验证码
     */
    private String verificationCode;
}
