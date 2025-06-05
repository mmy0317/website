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
     * 用户code
     */
    private String userCode;

    /**
     * 登录code、登录用户Id、三方Id
     */
    private String accountLoginCode;

    /**
     * 微信开放平台unionid
     */
    private String unionId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * C端网关token
     */
    private String ssoToken;

    /**
     * 三方授权token
     */
    private String accessToken;

    /**
     * 头像
     */
    private String headImgUrl;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登录类型
     * @SEE com.souche.retail.finance.csite.enums.LoginTypeEnum
     */
    private String loginType;

    /**
     * 绑定状态
     */
    private String bindStatus;

    /**
     * 绑定订单号
     */
    private String bindOrderNo;
}
