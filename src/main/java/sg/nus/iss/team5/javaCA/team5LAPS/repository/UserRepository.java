package sg.nus.iss.team5.javaCA.team5LAPS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team5.javaCA.team5LAPS.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT DISTINCT s2.name FROM User u, Staff s1, Staff s2 WHERE u.staffId = s1.staffId AND s1.managerId = s2.staffId AND u.userId=:uid")
  List<String> findManagerNamesByUID(@Param("uid") int userId);
  
  @Query("SELECT u FROM User u WHERE u.name=:username AND u.password=:pwd")
  User findUserByNamePwd(@Param("username")String username, @Param("pwd")String pwd);
  
  @Modifying
  @Query("update User u set u.password = :newPwd where u.userId = :uid")
  void updatePwd(@Param("uid")int uid, @Param("newPwd")String newPwd);
}