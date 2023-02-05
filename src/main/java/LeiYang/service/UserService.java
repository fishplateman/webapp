package LeiYang.service;

import LeiYang.Util.ReturnObject;
import LeiYang.dao.UserDao;
import LeiYang.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public void save(User user){
        userDao.save(user);
    }


    public User getById(long id){
        return userDao.getById(id);
    }

    public void update(String fname, String lname, String psw, long id) {
        userDao.update(fname, lname, psw, id);
    }
    public User find(String email){
        User user = userDao.findByName(email);
        return user;
    }


    public User get(Long id){
        User result = userDao.findOne(id);
        return result;
    }
}
