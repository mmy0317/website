package website.project.website.domain.param;


import lombok.Data;

import java.io.Serializable;

/**
 * 管理员分页查询参数
 * @author mayang
 */
@Data
public class AdministratorPageParam implements Serializable {

    private static final long serialVersionUID = -1433757331409150591L;

    private Integer page;

    private Integer size;

}
