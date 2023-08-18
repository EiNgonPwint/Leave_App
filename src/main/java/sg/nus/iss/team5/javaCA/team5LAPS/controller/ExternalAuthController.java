package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.team5.javaCA.team5LAPS.model.User;
import sg.nus.iss.team5.javaCA.team5LAPS.model.UserSession;
import sg.nus.iss.team5.javaCA.team5LAPS.service.UserService;

@RestController
@RequestMapping("/api")
public class ExternalAuthController {

	@Autowired
	  private UserService userService;
	
	  @CrossOrigin(origins = "http://localhost:3000")
	 @PostMapping("/auth")
	 public ResponseEntity<User> Auth(@RequestBody User user)
	 { 	User u =  userService.authenticate(user.getName(), user.getPassword());
	 	UserSession userSession = new UserSession();
	 	userSession.setUser(u);
	 	return new ResponseEntity<User>(u,HttpStatus.OK);
	 }
}
