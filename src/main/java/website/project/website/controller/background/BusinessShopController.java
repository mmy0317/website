package website.project.website.controller.background;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import website.project.website.convert.BusinessShopConvert;
import website.project.website.convert.MerchandiseConvert;
import website.project.website.domain.dto.BusinessShopOperateDTO;
import website.project.website.domain.dto.MerchandisePageDTO;
import website.project.website.domain.param.BusinessShopOperateParam;
import website.project.website.domain.param.MerchandisePageParam;
import website.project.website.service.BusinessShopService;
import website.project.website.utils.WebResponse;

/**
 * 营业点
 * @author mayang
 */
@RestController
@RequestMapping("/businessShop")
public class BusinessShopController {

    @Resource
    private BusinessShopService businessShopService;

    /**
     * 营业点分页查询
     * @return
     */
    @PostMapping("/page")
    public WebResponse<Void> businessShopPage(){
        return WebResponse.success();
    }

    /**
     * 新增或修改营业点
     * @return
     */
    @PostMapping("/addOrUpdate")
    public WebResponse<Void> addOrUpdateBusinessShop(@RequestBody BusinessShopOperateParam businessShopOperateParam){
        BusinessShopOperateDTO businessShopOperateDTO = BusinessShopConvert.INSTANCE.businessShopParamToDto(businessShopOperateParam);
        businessShopService.addOrUpdateBusinessShop(businessShopOperateDTO);
        return WebResponse.success();
    }


    /**
     * 删除营业点
     * @return
     */
    @GetMapping("/del")
    public WebResponse<Void> deleteBusinessShop(@RequestParam("shopId")Long shopId){
        businessShopService.deleteBusinessShop(shopId);
        return WebResponse.success();
    }


}
