package LeiYang.controller;

import LeiYang.Util.Common;
import LeiYang.Util.ExceptionMessage;
import LeiYang.Util.ResponseCode;
import LeiYang.Util.ReturnObject;
import LeiYang.entity.User;
import LeiYang.entity.UserVo;
import LeiYang.service.UserService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    //test url
    @RequestMapping("/hello")
    public String getUser(){
        return "hello";
    }

    @Resource
    private UserService userService;

    @PostMapping
    public ExceptionMessage add(@RequestParam("fname") String fname,@RequestParam("lname") String lname,@RequestParam("psw") String psw,@RequestParam("email") String email){
        User user = new User(fname,lname,email,psw);
        userService.save(user);
        return new ExceptionMessage().success();
    }
    @PutMapping
    public ExceptionMessage update(@RequestParam("fname") String fname,@RequestParam("lname") String lname,@RequestParam("psw") String psw){
        User user = new User(fname,lname,psw);
        userService.save(user);
        return new ExceptionMessage().success();
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        User user = userService.get(id);
        return user;
    }


}
