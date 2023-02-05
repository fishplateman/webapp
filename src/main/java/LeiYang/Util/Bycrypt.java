package LeiYang.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bycrypt {
    public static String encryptPassword(String rawPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean matchesPassword(String rawPassword, String encodePassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword,encodePassword);

    }
}