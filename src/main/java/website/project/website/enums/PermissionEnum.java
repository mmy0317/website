package website.project.website.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户权限枚举
 * @author mayang
 */
@AllArgsConstructor
@Getter
public enum PermissionEnum {

    ROOT(0,"超级管理员"),

    ADMIN(1,"管理员"),

    ORDINARY(2,"普通用户");


    private Integer code;

    private String permission;

    public static PermissionEnum getEnumByName(String name){
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (permissionEnum.name().equals(name)) {
                return permissionEnum;
            }
        }
        return null;
    }

}
