package sg.nus.iss.team5.javaCA.team5LAPS.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.team5.javaCA.team5LAPS.exception.StaffNotFound;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.model.UserSession;
import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveTypeService;

@Controller
@RequestMapping(value="/admin/manage")
@SessionAttributes(value = {"usession"}, types = {UserSession.class})
public class AdminController {
	
	 @Autowired
	  private LeaveTypeService leaveTypeService;
	 
	 @RequestMapping(value="/leave")
	  public String manageLeaveType(Model model) {
		  List<LeaveType> leaveTypes= leaveTypeService.findAllLeaveType();
		  model.addAttribute("some",leaveTypes);
		  return"manage-leave-type";
	  }
	 
	 @GetMapping("/newleave")
	 public String newLeaveType(Model model) {
		 model.addAttribute("leaveType", new LeaveType());
		 return "new-leavetype";
		 
	 }
	 
	 @PostMapping("/newleave")
	 public String newLeaveType(@ModelAttribute @Valid LeaveType leaveType,
			 					BindingResult result) {
		 if(result.hasErrors()) {
			 return "new-leavetype";
		 }
		 leaveTypeService.createLeaveType(leaveType);
		 
		 return "redirect:/admin/manage/leave";
	 }

	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	  public String editLeaveTypePage(@PathVariable int id, Model model) {
	    LeaveType leaveType = leaveTypeService.findLeaveType(id);
	    model.addAttribute("leaveType", leaveType);
	    
	    return "leavetype-edit";
	  }

	  @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	  public String editLeaveTypePage(@ModelAttribute @Valid LeaveType leaveType, BindingResult result,
	      @PathVariable int id) {
	    if (result.hasErrors()) {
	      return "leavetype-edit";
	    }
	      
	    leaveTypeService.changeLeaveType(leaveType);

	    
	    return "redirect:/admin/manage/leave";
	  }
	  
	  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	  public String deleteLeaveType(@PathVariable int id)
	    {
		  LeaveType leaveType = leaveTypeService.findLeaveType(id);
		  leaveTypeService.removeLeaveType(leaveType);
	    
	    
	    return "forward:/admin/manage/leave";
	  }
	 
	 
	 

	 
	 
	 
}
