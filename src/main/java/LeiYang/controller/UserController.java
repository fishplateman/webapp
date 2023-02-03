package LeiYang.controller;

import LeiYang.Utils.ExceptionMessage;
import LeiYang.entity.User;
import LeiYang.Utils.ExpressionHandler;
import LeiYang.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
//    @RequestMapping("/hello")
//    public String getUser(){
//        return "hello";
//    }
    @Resource
    private UserService userService;
    @PostMapping
    public ExpressionHandler add(@RequestBody User user){
        userService.save(user);
        return new ExpressionHandler();
    }


}
