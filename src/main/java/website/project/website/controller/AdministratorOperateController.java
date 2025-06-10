package website.project.website.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import website.project.website.domain.param.AdministratorAddParam;
import website.project.website.domain.vo.UserVO;
import website.project.website.service.AdministratorOperateService;
import website.project.website.utils.WebResponse;

/**
 * 管理员用户操作
 * @author mayang
 */
@RestController
@RequestMapping("/administrator/operate")
public class AdministratorOperateController {

    @Resource
    private AdministratorOperateService administratorUserService;

    /**
     * 增加管理员用户
     * @param param
     * @return
     */
    @PostMapping("/addUser")
    public WebResponse<Void> addAdministratorUser(@RequestBody AdministratorAddParam param){
        administratorUserService.addAdministratorUser(param.getName(), param.getMobilePhone(), param.getPassWord());
        return WebResponse.success();
    }

    /**
     * 删除管理员用户
     * @param userId
     * @return
     */
    @GetMapping("/delUser")
    public WebResponse<Void> delAdministratorUser(@RequestParam("userId") String userId){
        administratorUserService.delAdministratorUser(userId);
        return WebResponse.success();
    }


    @GetMapping("/test")
    public WebResponse<String> test(){
        return WebResponse.success("成功");
    }


}
