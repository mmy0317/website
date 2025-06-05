package website.project.website.domain.param;

import lombok.Data;

/**
 * 登录请求参数
 * @author mayang
 */
@Data
public class LoginParam {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String passCode;

    /**
     * 手机短信验证码
     */
    private String verificationCode
    ;
}
