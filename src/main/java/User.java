import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private String last_name;
    private String psw;
    private String first_name;
    private String email_address;
    String pw_hash = BCrypt.hashpw("Yahoo", BCrypt.gensalt());
    BCryptPasswordEncoder a = new BCryptPasswordEncoder();
    boolean f = a.matches("000","8098");

}