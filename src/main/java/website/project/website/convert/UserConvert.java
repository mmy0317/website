package website.project.website.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import website.project.website.domain.dto.UserDTO;
import website.project.website.entity.UserDO;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mappings({
            @Mapping(expression="java(website.project.website.enums.PermissionEnum.getEnumByName(userDO.getPermission()))",target="permission"),
            @Mapping(expression="java(website.project.website.enums.AdministratorStateEnum.getEnumByCode(userDO.getState()))",target="state")
    })
    UserDTO userDoToDto(UserDO userDO);

}
