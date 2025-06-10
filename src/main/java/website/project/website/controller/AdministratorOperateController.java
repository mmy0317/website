package website.project.website.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import website.project.website.domain.param.AdministratorAddParam;
import website.project.website.domain.vo.UserVO;
import website.project.website.service.AdministratorOperateService;
import website.project.website.utils.WebResponse;

/**
 * 管理员用户操作
 * 当前controller中所有操作都需要root权限
 * @author mayang
 */
@RestController
@RequestMapping("/administrator/operate")
public class AdministratorOperateController {

    @Resource
    private AdministratorOperateService administratorUserService;

    /**
     * 创建管理员用户
     * @param param
     * @return
     */
    @PostMapping("/addUser")
    public WebResponse<Void> addAdministratorUser(@RequestBody AdministratorAddParam param){
        administratorUserService.addAdministratorUser(param.getName(), param.getMobilePhone(), param.getPassWord());
        return WebResponse.success();
    }

    /**
     * 注销管理员用户
     * @param userId
     * @return
     */
    @GetMapping("/delUser")
    public WebResponse<Void> delAdministratorUser(@RequestParam("userId") String userId){
        administratorUserService.delAdministratorUser(userId);
        return WebResponse.success();
    }

    /**
     * 查询管理员用户明文密码
     * @param userId
     * @return
     */
    @GetMapping("/getUserPwd")
    public WebResponse<String> getAdministratorUserPwd(@RequestParam("userId") String userId){
        String result = administratorUserService.getAdministratorUserPwd(userId);
        return WebResponse.success(result);
    }



}
