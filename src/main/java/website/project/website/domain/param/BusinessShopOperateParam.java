package website.project.website.domain.param;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 营业点新增/修改请求参数
 * @author mayang
 */
@Data
public class BusinessShopOperateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营业点id
     */
    private Long id;

    /**
     * 营业点名称
     */
    private String shopName;

    /**
     * 营业点联系方式
     */
    private List<String> shopConnects;

    /**
     * 营业点详细地址
     */
    private String address;
}
