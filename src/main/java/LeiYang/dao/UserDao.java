package LeiYang.dao;


import LeiYang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserDao extends JpaRepository<User,Long> {
    @Query(value = "SELECT *FROM user where email_address = ?1", nativeQuery = true)
    User findByName(String name);

    @Query(value = "SELECT *FROM user where id = ?1", nativeQuery = true)
    User getById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET first_name = ?1 , last_name = ?2  , password = ?3 WHERE id = ?4", nativeQuery = true)
    int update(String fname, String lname, String password, long id);


}
