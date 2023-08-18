package sg.nus.iss.team5.javaCA.team5LAPS.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.team5.javaCA.team5LAPS.model.User;

@Component
public class UserValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    System.out.println(target);
    
    ValidationUtils.rejectIfEmpty(errors, "userId", "error.user.userid.empty");
    ValidationUtils.rejectIfEmpty(errors, "staffId", "error.user.staffid.empty");
//    ValidationUtils.rejectIfEmpty(errors, "name", "error.user.name.empty");
//    ValidationUtils.rejectIfEmpty(errors, "password", "error.user.password.empty");
  }

}