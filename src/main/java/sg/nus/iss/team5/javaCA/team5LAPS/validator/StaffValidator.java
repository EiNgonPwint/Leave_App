package sg.nus.iss.team5.javaCA.team5LAPS.validator;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;

@Component
public class StaffValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return Staff.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    System.out.println(target);

    EmailValidator validator = EmailValidator.getInstance();
    Staff staff = (Staff) target;

    if(!validator.isValid(staff.getEmail())){
      errors.rejectValue("email", "errors.email", "Invalid email");
    }
    
    ValidationUtils.rejectIfEmpty(errors, "email", "error.staff.email.empty");
    ValidationUtils.rejectIfEmpty(errors, "staffId", "error.staff.staffid.empty");
    ValidationUtils.rejectIfEmpty(errors, "name", "error.staff.name.empty");
  }

}