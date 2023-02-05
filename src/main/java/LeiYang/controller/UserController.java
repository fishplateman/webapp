package LeiYang.controller;

import LeiYang.Util.Bycrypt;
import LeiYang.Util.ExceptionMessage;
import LeiYang.entity.User;
import LeiYang.entity.UserVo;
import LeiYang.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
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
    public ExceptionMessage add(@RequestParam("fname") String fname,@RequestParam("lname") String lname,@RequestParam("psw") String psw,@RequestParam("email") String email){
        if(userService.find(email) != null){
            return new ExceptionMessage().fail();
        }
        else{
            String password = Bycrypt.encryptPassword(psw);
            //System.out.println(password);
            User user = new User(fname,lname,email,password);
            userService.save(user);
            return new ExceptionMessage().success();
        }
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
