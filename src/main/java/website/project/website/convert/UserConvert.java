package website.project.website.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.project.website.domain.dto.RegisterDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.RegisterParam;
import website.project.website.entity.UserDO;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    RegisterDTO registerParam2RegisterDTO(RegisterParam registerParam);

    UserDO registerDto2UserDO(RegisterDTO registerDTO);

    UserDTO userDo2Dto(UserDO userDO);
}
