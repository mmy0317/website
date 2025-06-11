package website.project.website.controller.background;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import website.project.website.convert.MerchandiseConvert;
import website.project.website.domain.dto.MerchandisePageDTO;
import website.project.website.domain.param.MerchandisePageParam;
import website.project.website.service.MerchandiseService;
import website.project.website.utils.WebResponse;

/**
 * 商品相关
 * @author mayang
 */
@RestController
@RequestMapping("/merchandise")
public class MerchandiseController {

    @Resource
    private MerchandiseService merchandiseService;

    /**
     * 商品列表分页查询
     * @return
     */
    @PostMapping("/page")
    public WebResponse<Void> merchandisePage(@RequestBody MerchandisePageParam merchandisePageParam){
        MerchandisePageDTO dto = MerchandiseConvert.INSTANCE.merchandisePageParamToDto(merchandisePageParam);
        return WebResponse.success();
    }

    /**
     * 新增或修改商品
     * @return
     */
    @PostMapping("/addOrUpdate")
    public WebResponse<Void> addOrUpdateMerchandise(){
        return WebResponse.success();
    }

    /**
     * 上架或下架商品
     * @return
     */
    @PostMapping("/pushOrPull")
    public WebResponse<Void> pushOrPullMerchandise(){
        return WebResponse.success();
    }


    /**
     * 删除商品
     * @return
     */
    @GetMapping("/del")
    public WebResponse<Void> deleteMerchandise(){
        return WebResponse.success();
    }

}
