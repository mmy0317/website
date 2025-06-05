package website.project.website.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String userId;

    private String account;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

}
