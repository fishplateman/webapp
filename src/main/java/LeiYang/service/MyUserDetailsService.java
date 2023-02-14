package LeiYang.service;

import LeiYang.dao.UserDao;
import LeiYang.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("roles");
        Users users = userDao.findByName(s);
        if(users == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new User (users.getEmail_address(), users.getPassword(),auths);
    }

}
