package LeiYang.entity;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;


//CREATE TABLE `CSYE6225`.`user` (
//        `id` BIGINT  PRIMARY KEY NOT NULL AUTO_INCREMENT,
//        `account_created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//        `account_updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//        `email_address` VARCHAR(100) NOT NULL,
//        `password` VARCHAR(25) NOT NULL,
//        `first_name` VARCHAR(20) NOT NULL,
//        `last_name` VARCHAR(20) NOT NULL
//        );



@Table(name = "user")
@Entity
public class User {
    @Column(name = "last_name")
    private String lastName;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email_address")
    private String emailAddress;

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String psw) {
        this.password = psw;
    }

    public String getFirst_name() {
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getEmail_address() {
        return emailAddress;
    }

    public void setEmail_address(String email_address) {
        this.emailAddress = email_address;
    }
    //    String pw_hash = BCrypt.hashpw("Yahoo", BCrypt.gensalt());
//    BCryptPasswordEncoder a = new BCryptPasswordEncoder();
//    boolean f = a.matches("000","8098");

}