package sg.nus.iss.team5.javaCA.team5LAPS.validator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.UserSession;
import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveServiceImpl;
import sg.nus.iss.team5.javaCA.team5LAPS.service.StaffLeaveBalanceService;

@Component
public class LeaveValidator implements Validator {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	public String StaffId;
	
	@Autowired
	private StaffLeaveBalanceService staffLeaveBalanceService;

  @Override
  public boolean supports(Class<?> clazz) {
    return Leaves.class.isAssignableFrom(clazz);

  }

  @Override
	public void validate(Object target, Errors errors) {
		
		double duration = 0;
		Leaves leave = (Leaves) target;
		System.out.print("-------"+leave.getFromDate());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "leaveType", "error.leaveType", "Leave type is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDate", "error.fromDate", "From Date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDate", "error.toDate", "To Date is required.");
		if ((leave.getFromDate() != null || leave.getToDate() != null)
				&& (leave.getFromDate().compareTo(leave.getToDate()) > 0)) {
			System.out.print("inside if-----");
			errors.reject("toDate", "End date should be greater than start date.");
			errors.rejectValue("toDate", "error.dates", "to date must be > from date");
		}
		if(leave.getFromDate() != null || leave.getToDate() != null) {
			System.out.print("inside else -------");
			double validDays = staffLeaveBalanceService.findStaffLeaveBalanceByStaffId(leave.getStaffId(),leave.getLeaveType().getLeaveTypeId());
			
			int holsAndWeekendCounter  = leaveService.getWeekendAndPublicHoliday(leave);

			long totalDays = leaveService.numberOfDays(leave.getFromDate(), leave.getToDate());

			if (totalDays <= 14) {
				duration = totalDays - holsAndWeekendCounter;
			} else {
				duration = totalDays;
			}
			boolean holidayCheckFromDate= leaveService.checkHoliday(leave.getFromDate());
			boolean holidayCheckToDate= leaveService.checkHoliday(leave.getToDate());
			if(holidayCheckFromDate==true) {
				errors.reject("fromDate","From Date must be workingday");
				errors.rejectValue("fromDate","error.fromDate","From Date Date must be workingday");
				
			}
			if(holidayCheckToDate==true) {
				errors.reject("toDate","To Date must be workingday");
				errors.rejectValue("toDate","error.toDate","To Date must be workingday");
				
			}
			if (duration > validDays) {
				errors.reject("duration", "Greater than the valid allowance days");
				errors.rejectValue("duration", "error.duration", "Greater than the valid allowance days");
			}

			leave.setDuration(duration);
		}

		
		
	}
	
	public void setStaffID(String id) {
		this.StaffId = id;
	}

}