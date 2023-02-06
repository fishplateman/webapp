package LeiYang.controller;

import LeiYang.util.Bycrypt;
import LeiYang.util.EmailVerify;
import LeiYang.util.ExceptionMessage;
import LeiYang.entity.User;
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
                User user = new User(userVo.getFirstName(), userVo.getLastName(), userVo.getEmail(),password);
                userService.save(user);
                return new ExceptionMessage().success();
            }
        }
        return new ExceptionMessage().fail();
    }
    @PutMapping("/v1/user/{id}")
    public ExceptionMessage update(@RequestBody UserVo user,@PathVariable Long id){
        String password = Bycrypt.encryptPassword(user.getPassword());
        userService.update(user.getFirstName(), user.getLastName(), password, id);
        return new ExceptionMessage().success();
    }

    @GetMapping("/v1/user/{id}")
    public User getUser(@PathVariable Long id){
        User user = userService.get(id);
        return user;
    }

}
