package LeiYang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


@Table(name = "user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Users {
    public Users() {
    }

    public Users(String fname, String lname, String psw) {
        this.password = psw;
        this.firstName = fname;
        this.lastName = lname;
    }
    public Users(String fname, String lname, String email, String psw) {
        this.password = psw;
        this.emailAddress = email;
        this.firstName = fname;
        this.lastName = lname;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;
    @JsonIgnore
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email_address")
    private String emailAddress;
    @CreatedDate
    @Column(name = "account_created")
    private Date createdTime;
    @LastModifiedDate
    @Column(name = "account_updated")
    private Date updateTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp getGmtCreate) {
        this.createdTime = getCreatedTime();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateGmtCreate) {
        this.updateTime = updateGmtCreate;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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