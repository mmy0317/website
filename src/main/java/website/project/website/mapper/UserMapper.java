package website.project.website.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import website.project.website.domain.dto.AdministratorPageDTO;
import website.project.website.entity.UserDO;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO>{

    /**
     * 根据user_id查询用户信息
     * @param userId
     * @return
     */
    UserDO selectByUserId(@Param("userId") String userId);

    /**
     * 根据手机号查询用户信息
     * @param mobilePhone
     * @return
     */
    UserDO selectByPhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 管理员权限用户分页查询
     * @param page
     * @param administratorPageDTO
     * @return
     */
    IPage<UserDO> selectAdministratorPage(Page<UserDO> page, @Param("dto") AdministratorPageDTO administratorPageDTO);
}
