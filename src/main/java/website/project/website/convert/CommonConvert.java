package website.project.website.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.param.LoginParam;

/**
 * 通用convert
 * @author mayang
 */
@Mapper
public interface CommonConvert {

    CommonConvert INSTANCE = Mappers.getMapper(CommonConvert.class);

    LoginInfoDTO loginParamToDTO(LoginParam loginParam);


}
