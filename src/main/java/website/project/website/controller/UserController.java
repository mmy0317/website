//package website.project.website.controller;
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import website.project.website.convert.UserConvert;
//import website.project.website.domain.dto.UserDTO;
//import website.project.website.domain.vo.UserVO;
//import website.project.website.interceptor.AuthNHolder;
//import website.project.website.utils.WebResponse;
//
///**
// * 用户个人信息查询控制器
// */
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//
//    /**
//     * 查询用户个人信息
//     * @return
//     */
//    @GetMapping("/info")
//    public WebResponse<UserVO> owner(){
//        UserDTO userDTO = AuthNHolder.user();
//        UserVO userVO = UserConvert.INSTANCE.userDto2Vo(userDTO);
//        return  WebResponse.success(userVO);
//    }
//
//    /**
//     * 更新用户个人信息
//     * @return
//     */
//    @PostMapping("/update")
//    public WebResponse<Void> update(){
//        return WebResponse.success();
//    }
//
//    /**
//     * 更新用户密码
//     * @return
//     */
//    @PostMapping("/update/password")
//    public WebResponse<Void> updatePassword(){
//        return WebResponse.success();
//    }
//
//}
