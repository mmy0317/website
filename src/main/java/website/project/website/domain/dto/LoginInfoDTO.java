package website.project.website.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDTO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 手机短信验证码
     */
    private String verificationCode;

}
