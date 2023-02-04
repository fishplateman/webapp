package LeiYang.service;

import LeiYang.Util.ReturnObject;
import LeiYang.dao.UserDao;
import LeiYang.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public void save(User user){
        userDao.save(user);
    }


    public User get(Long id){
        User result = userDao.findOne(id);
        return result;
    }
}
