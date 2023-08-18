package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Role;
import sg.nus.iss.team5.javaCA.team5LAPS.model.User;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;
  
  @Transactional
  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  @Override
  public User findUser(int userId) {
    return userRepository.findById(userId).orElse(null);
  }
  
  @Transactional
  @Override
  public void changePwd(int uid, String newPwd) {
      userRepository.updatePwd(uid, newPwd);
  }

  @Transactional
  @Override
  public User createUser(User user) {
	  user.setIsActive(true);
    return userRepository.saveAndFlush(user);
  }

  @Transactional
  @Override
  public User changeUser(User user) {
	  user.setIsActive(true);
    return userRepository.saveAndFlush(user);
  }

  @Transactional
  @Override
  public void removeUser(User user) {
    boolean status = user.getIsActive();
		if (status == false){
			user.setIsActive(true);
		}
		else {
			user.setIsActive(false);
    }
    userRepository.saveAndFlush(user);
    //userRepository.delete(user);
  }

  @Transactional
  @Override
  public List<Role> findRolesForUser(int userId) {
    User user = userRepository.findById(userId).orElse(null);
    
    if (user == null) {
      return new ArrayList<Role>();
    }
    
    return user.getRoleSet();
  }

  @Transactional
  @Override
  public List<String> findRoleNamesForUser(int userId) {
    List<Role> roles = findRolesForUser(userId);
    
    List<String> roleNames = new ArrayList<>();
    roles.forEach(role -> roleNames.add(role.getName()));
    
    return roleNames;
  }

  @Transactional
  @Override
  public List<String> findManagerNameByUID(int userId) {
    return userRepository.findManagerNamesByUID(userId);
  }

  @Transactional
  @Override
  public User authenticate(String username, String pwd) {
    User u = userRepository.findUserByNamePwd(username, pwd);
    if (u.getIsActive() == false){
      return null;
    }
    return u;
  }
}
