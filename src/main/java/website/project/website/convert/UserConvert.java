package website.project.website.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import website.project.website.domain.dto.LoginInfoDTO;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.LoginParam;
import website.project.website.domain.vo.UserVO;
import website.project.website.entity.UserDO;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserDTO userDoToDto(UserDO userDO);

    LoginInfoDTO loginParam2LoginInfoDTO(LoginParam loginParam);

    UserVO userDto2Vo(UserDTO userDTO);
}
