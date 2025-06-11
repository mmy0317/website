package website.project.website.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class BusinessShopOperateDTO {

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


