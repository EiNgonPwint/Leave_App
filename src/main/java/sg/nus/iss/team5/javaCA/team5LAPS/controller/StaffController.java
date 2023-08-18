package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.nus.iss.team5.javaCA.team5LAPS.exception.StaffNotFound;
import sg.nus.iss.team5.javaCA.team5LAPS.model.*;
import sg.nus.iss.team5.javaCA.team5LAPS.service.*;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.StaffLeaveBalanceRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.validator.StaffValidator;
@Controller
@RequestMapping("/admin/staff")
@SessionAttributes(value = {"usession"}, types = {UserSession.class})
public class StaffController {

  @Autowired
  private StaffService staffService;
  @Autowired
  private StaffValidator staffValidator;
  
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;
  
  @Autowired
  private LeaveTypeService leaveTypeService;
 
  @Autowired
  private StaffLeaveBalanceService slbr;
  @InitBinder("staff")
  private void initStaffBinder(WebDataBinder binder) {
    binder.addValidators(staffValidator);
  }


  
  @RequestMapping(value = "/list")
  public String staffListPage(Model model) {
    List<Staff> staffList = staffService.findAllStaffs();
    model.addAttribute("staffList", staffList);
    
    return "staff-list";
  }
  
  
  
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String newStaffPage(Model model) {
	  model.addAttribute("staff", new Staff());
	    List<Integer> managerIds=staffService.findAllStaffIDs();
	    managerIds.add(0,0);
	    model.addAttribute("sidlist", managerIds );
	    
	    return "staff-new";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createNewStaff(@ModelAttribute @Valid Staff staff, BindingResult result, Model model) {
    if (result.hasErrors()) {
    	
//    	List<String> aa= new ArrayList<>();
//    	aa.add("a");
//    	aa.add("b");
//    	aa.ad
    	
    	List<Integer> managerIds=staffService.findAllStaffIDs();
        managerIds.add(0,0);
        List<Integer> sidList = staffService.findAllStaffIDs();
        model.addAttribute("sidlist", sidList);
        
        

      model.addAttribute("sidlist", managerIds);
      return "staff-new";
    }
    
//    List<Role> newRoleSet = new ArrayList<Role>();
//    user.getRoleSet().forEach(role -> {
//      Role completeRole = roleService.findRole(role.getRoleId());
//      newRoleSet.add(completeRole);
//    });
//    user.setRoleSet(newRoleSet);
//   
//
//    userService.createUser(user);
//    
    staffService.createStaff(staff);
    Role staffRole=roleService.save(new Role("staff", "Staff", "Staff members",true));
    User u = new User();
    u.setStaffId(staff.getStaffId());
    u.setName(staff.getName());
    u.setPassword("password");
    u.setRoleSet(Arrays.asList(staffRole));

    userService.createUser(u);
    
    
//    Staff s1=new Staff(staff.getName(),staff.getManagerId(),staff.getEmail());
    
    StaffLeaveBalance slb1 = new StaffLeaveBalance(leaveTypeService.findLeaveType(1),staff,60); 
   
    slbr.createLeaveBalance(slb1);
    
    StaffLeaveBalance slb2 = new StaffLeaveBalance(leaveTypeService.findLeaveType(2),staff,14); 
    
    slbr.createLeaveBalance(slb2);
    
    StaffLeaveBalance slb3 = new StaffLeaveBalance(leaveTypeService.findLeaveType(3),staff,0.0); 
    slbr.createLeaveBalance(slb3);
    String message = "New staff " + staff.getStaffId() + " was successfully created.";
    System.out.println(message);
    
    return "redirect:/admin/staff/list";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String editStaffPage(@PathVariable int id, Model model) {
    Staff staff = staffService.findStaff(id);
    model.addAttribute("staff", staff);
    List<Integer> managerIds=staffService.findStaffIDsExclusion(id);
    managerIds.add(0,0);
    model.addAttribute("sidlist", managerIds);
    //model.addAttribute("sidlist", staffService.findAllStaffIDs());
    
    return "staff-edit";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
  public String editStaff(@ModelAttribute @Valid Staff staff, BindingResult result,
      @PathVariable String id) throws StaffNotFound {
    if (result.hasErrors()) {
      return "staff-edit";
    }
      
    staffService.changeStaff(staff);
    
    String message = "Staff was successfully updated.";
    System.out.println(message);
    
    return "redirect:/admin/staff/list";
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public String deleteStaff(@PathVariable int id)
      throws StaffNotFound {
    Staff staff = staffService.findStaff(id);
    staffService.removeStaff(staff);
    
    String message = "The staff " + staff.getStaffId() + " was successfully deleted.";
    System.out.println(message);
    
    return "forward:/admin/staff/list";
  }
}
