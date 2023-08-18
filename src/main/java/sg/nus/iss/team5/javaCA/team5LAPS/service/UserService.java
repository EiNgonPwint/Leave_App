package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Role;
import sg.nus.iss.team5.javaCA.team5LAPS.model.User;

public interface UserService {

  List<User> findAllUsers();
  User findUser(int userId);
  User createUser(User user);
  User changeUser(User user);
  void removeUser(User user);

  List<Role> findRolesForUser(int userId);
  List<String> findRoleNamesForUser(int userId);
  List<String> findManagerNameByUID(int userId);
  void changePwd(int uid, String newPwd);
  
  /**
   * Return the respective User object if username and password are correct, null otherwise
   * @param username
   * @param pwd
   * @return
   */
  User authenticate(String username, String pwd);
}