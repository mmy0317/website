package website.project.website.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.project.website.domain.dto.MerchandisePageDTO;
import website.project.website.domain.param.MerchandisePageParam;

@Mapper
public interface MerchandiseConvert {

    MerchandiseConvert INSTANCE = Mappers.getMapper(MerchandiseConvert.class);


    MerchandisePageDTO merchandisePageParamToDto(MerchandisePageParam merchandisePageParam);
}
