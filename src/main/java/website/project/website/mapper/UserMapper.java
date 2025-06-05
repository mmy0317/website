package website.project.website.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import website.project.website.entity.UserDO;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO>{

    UserDO selectUserDoByAccount(@Param("account") String account);

    UserDO selectUserDoByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    UserDO selectByUserId(@Param("userId") String userId);
}
