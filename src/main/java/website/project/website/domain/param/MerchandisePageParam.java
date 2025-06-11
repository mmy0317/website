package website.project.website.domain.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品分页请求参数
 * @author mayang
 */
@Data
public class MerchandisePageParam implements Serializable {

    private static final long serialVersionUID = -1433757331409150591L;

    private Integer page;

    private Integer size;

}
