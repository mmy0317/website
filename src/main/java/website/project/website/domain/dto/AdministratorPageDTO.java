package website.project.website.domain.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 管理员分页查询参数
 * @author mayang
 */
@Data
public class AdministratorPageDTO implements Serializable {

    private static final long serialVersionUID = -1433757331409150591L;

    /**
     * 模糊查询字段
     */
    private String searchValue;

    /**
     * 分页参数
     */
    private Integer page;

    /**
     * 分页参数
     */
    private Integer size;

}
