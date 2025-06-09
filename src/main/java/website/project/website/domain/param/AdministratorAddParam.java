package website.project.website.domain.param;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * 增加管理员用户请求参数
 * @author mayang
 */
@Data
public class AdministratorAddParam implements Serializable {

    private static final long serialVersionUID = -1433757331409150591L;

    /**
     * 手机号
     */
    @NotNull("手机号不能为空")
    private String mobilePhone;

    /**
     * 密码
     */
    @NotNull("密码不能为空")
    private String passWord;

    /**
     * 姓名
     */
    @NotNull("姓名不能为空")
    private String name;

}
