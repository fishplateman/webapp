package LeiYang.controller;

import LeiYang.entity.Users;
import LeiYang.service.MyUserDetailsService;
import LeiYang.util.Bycrypt;
import LeiYang.util.EmailVerify;
import LeiYang.util.ExceptionMessage;
import LeiYang.entity.UserVo;
import LeiYang.service.UserService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    //test url
    @RequestMapping("/healthz")
    public ExceptionMessage responseHealth(){
        return new ExceptionMessage().success();
    }

    @Resource
    private UserService userService;

    @PostMapping("/v1/user")
    public ExceptionMessage add(@RequestBody UserVo userVo){
        if(EmailVerify.isValidEmail(userVo.getEmail())){
            if(userService.find(userVo.getEmail()) != null){
                return new ExceptionMessage().fail();
            }
            else{
                String password = Bycrypt.encryptPassword(userVo.getPassword());
                //System.out.println(password);
                Users users = new Users(userVo.getFirstName(), userVo.getLastName(), userVo.getEmail(),password);
                userService.save(users);
                return new ExceptionMessage().success();
            }
        }
        return new ExceptionMessage().fail();
    }

    //添加认证是否为本人
    @PutMapping("/v1/user/{id}")
    public ExceptionMessage update(@RequestBody UserVo user,@PathVariable Long id){
        String password = Bycrypt.encryptPassword(user.getPassword());
        userService.update(user.getFirstName(), user.getLastName(), password, id);
        return new ExceptionMessage().success();
    }

    @GetMapping("/v1/user/{id}")
    public Users getUser(@PathVariable Long id){
        Users users = userService.get(id);
        return users;
    }

}
