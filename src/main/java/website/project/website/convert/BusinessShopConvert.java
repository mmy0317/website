package website.project.website.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.project.website.domain.dto.BusinessShopOperateDTO;
import website.project.website.domain.param.BusinessShopOperateParam;

@Mapper
public interface BusinessShopConvert {

    BusinessShopConvert INSTANCE = Mappers.getMapper(BusinessShopConvert.class);

    BusinessShopOperateDTO businessShopParamToDto(BusinessShopOperateParam businessShopOperateParam);
}
