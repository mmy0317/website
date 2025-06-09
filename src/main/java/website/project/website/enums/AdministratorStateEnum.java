package website.project.website.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员用户账号状态
 * @author mayang
 */
@AllArgsConstructor
@Getter
public enum AdministratorStateEnum {
    DELETE(0, "删除"),
    NORMAL(1, "正常")
    ;

    private Integer code;

    private String desc;
}
