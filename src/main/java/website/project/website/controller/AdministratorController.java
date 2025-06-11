package website.project.website.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.AdministratorAddParam;
import website.project.website.domain.param.AdministratorPageParam;
import website.project.website.domain.vo.UserVO;
import website.project.website.service.AdministratorOperateService;
import website.project.website.utils.WebResponse;

/**
 * 管理员用户操作
 * 当前controller中所有操作都需要root权限
 * @author mayang
 */
@RestController
@RequestMapping("/administrator")
public class AdministratorController {


    @Resource
    private AdministratorOperateService administratorOperateService;

    /**
     * 创建管理员用户
     * @param param
     * @return
     */
    @PostMapping("/addUser")
    public WebResponse<Void> addAdministratorUser(@RequestBody AdministratorAddParam param){
        administratorOperateService.addAdministratorUser(param.getName(), param.getMobilePhone(), param.getPassWord());
        return WebResponse.success();
    }

    /**
     * 注销管理员用户
     * @param userId
     * @return
     */
    @GetMapping("/delUser")
    public WebResponse<Void> delAdministratorUser(@RequestParam("userId") String userId){
        administratorOperateService.delAdministratorUser(userId);
        return WebResponse.success();
    }

    /**
     * 查询管理员用户明文密码
     * @param userId
     * @return
     */
    @GetMapping("/getUserPwd")
    public WebResponse<String> getAdministratorUserPwd(@RequestParam("userId") String userId){
        String result = administratorOperateService.getAdministratorUserPwd(userId);
        return WebResponse.success(result);
    }

    /**
     * 分页查询管理员用户信息
     * @return
     */
    @PostMapping("/administrator/page")
    public WebResponse<Page<UserVO>> queryAdministratorUserPage(@RequestBody AdministratorPageParam administratorPageParam){
        Page<UserVO> result = new Page<>(administratorPageParam.getPage(),administratorPageParam.getPage());
        Page<UserDTO> resultPage = administratorOperateService.queryAdministratorUserPage(UserConvert.INSTANCE.AdministratorPageParamToDto(administratorPageParam));
        if (CollectionUtils.isEmpty(resultPage.getRecords())){
            return WebResponse.success(result);
        }
        result.setTotal(resultPage.getTotal());
        result.setRecords(UserConvert.INSTANCE.userDtoListToVoList(resultPage.getRecords()));
        return WebResponse.success(result);
    }



}
