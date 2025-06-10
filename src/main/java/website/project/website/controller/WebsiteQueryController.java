package website.project.website.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.project.website.convert.UserConvert;
import website.project.website.domain.dto.UserDTO;
import website.project.website.domain.param.AdministratorPageParam;
import website.project.website.domain.vo.UserVO;
import website.project.website.service.AdministratorOperateService;
import website.project.website.utils.WebResponse;

import java.util.List;

/**
 * 管理员后台权限查询请求控制器
 * @author mayang
 */
@Slf4j
@RestController
@RequestMapping("/website/query")
public class WebsiteQueryController {

    @Resource
    private AdministratorOperateService administratorOperateService;

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
