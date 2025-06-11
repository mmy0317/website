package website.project.website.service;

import website.project.website.domain.dto.BusinessShopOperateDTO;

public interface BusinessShopService {

    /**
     * 营业网点新增或修改
     * @param businessShopOperateDTO
     */
    void addOrUpdateBusinessShop(BusinessShopOperateDTO businessShopOperateDTO);

    /**
     * 营业网点删除
     * @param shopId
     */
    void deleteBusinessShop(Long shopId);
}
