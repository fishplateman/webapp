package LeiYang.service;

import LeiYang.dao.UserDao;
import LeiYang.entity.Users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public void save(Users users){
        userDao.save(users);
    }


    public Users getById(long id){
        return userDao.getById(id);
    }

    public void update(String fname, String lname, String psw, long id) {
        userDao.update(fname, lname, psw, id);
    }
    public Users find(String email){
        Users users = userDao.findByName(email);
        return users;
    }


    public Users get(Long id){
        Users result = userDao.findOne(id);
        return result;
    }
}
