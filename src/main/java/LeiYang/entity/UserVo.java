package LeiYang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
public class UserVo {
    @Column(name = "last_name")
    private String lastName;
    @JsonIgnore
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email_address")
    private String emailAddress;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    public UserVo() {
    }

    public UserVo(String fname, String lname, String email, String psw) {
        this.password = psw;
        this.emailAddress = email;
        this.firstName = fname;
        this.lastName = lname;
    }

}