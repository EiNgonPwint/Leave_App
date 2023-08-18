package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.team5.javaCA.team5LAPS.exception.LeaveNotFound;
import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveTypeService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.StaffLeaveBalanceService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.StaffService;
import sg.nus.iss.team5.javaCA.team5LAPS.model.EmailDetails;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveEnum;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.model.StaffLeaveBalance;
import sg.nus.iss.team5.javaCA.team5LAPS.model.UserSession;
import sg.nus.iss.team5.javaCA.team5LAPS.validator.LeaveValidator;

@Controller
@RequestMapping(value = "/staff")
public class LeaveController {
  @Autowired
  private LeaveService leaveService;
  
  @Autowired
  private StaffService staffService;
  
  @Autowired
  private StaffLeaveBalanceService staffLeaveBalanceService;

  @Autowired
  private LeaveValidator leaveValidator;
  
  @Autowired
  private LeaveTypeService leaveTypeService;

  @InitBinder("leave")
  private void initLeaveBinder(WebDataBinder binder) {
    binder.addValidators(leaveValidator);
  }


  
  @GetMapping(value = "leave/history")
  public String staffLeaveHistory(HttpSession session, Model model,  @RequestParam(defaultValue = "1") int page,
  @RequestParam(defaultValue = "5") int size,
  @RequestParam(defaultValue = "leaveId,asc") String[] sort) {
    try {

      UserSession usession = (UserSession) session.getAttribute("usession");
    
      System.out.println(usession.getStaff());
      List<Leaves> leaves = new ArrayList<Leaves>();
      
      String sortField = sort[0];
      String sortDirection = sort[1];
      
      Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
      Order order = new Order(direction, sortField);
      
      Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

      Page<Leaves> pageLvs;
        //pageLvs = leaveRepository.findAll(pageable);
        pageLvs = leaveService.findLeavesBySID(usession.getStaff().getStaffId(), pageable);
        leaves = pageLvs.getContent();
      
      model.addAttribute("lvhistory", leaves);
      model.addAttribute("currentPage", pageLvs.getNumber() + 1);
      model.addAttribute("totalItems", pageLvs.getTotalElements());
      model.addAttribute("totalPages", pageLvs.getTotalPages());
      model.addAttribute("pageSize", size);
      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDirection", sortDirection);
      model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }
    model.addAttribute("now",LocalDate.now());
    return "leave-my-history";
  }


  @GetMapping("/leave/details/{id}")
  public String staffLeaveDtails(@PathVariable int id, Model model) {
    Leaves leave = leaveService.findLeave(id);
    model.addAttribute("leave", leave);
   
    
    return "staff-leave-details";
  }

  @GetMapping("/leave/create")
  public String newLeavePage(Model model,HttpSession session) {
	  UserSession usession = (UserSession) session.getAttribute("usession");
    model.addAttribute("leave", new Leaves());
    Leaves leave = new Leaves();
	leave.setStaffId(usession.getStaff().getStaffId());
    List<LeaveType> leaveTypeNew = leaveTypeService.findAllLeaveType();
    model.addAttribute("leaveTypes", leaveTypeNew);
    model.addAttribute("leave", leave);
    return "leave-new";
  }

  @PostMapping("/leave/create")
  public String createNewLeave(@ModelAttribute @Valid Leaves leave, BindingResult result,
      Model model, HttpSession session) throws URISyntaxException {
    if (result.hasErrors()) {
    	model.addAttribute("leaveTypes", leaveTypeService.findAllLeaveType());
      return "leave-new";
    }
    
    UserSession usession = (UserSession) session.getAttribute("usession");
    Integer mgrId = usession.getStaff().getManagerId();
    Integer stfId = usession.getStaff().getStaffId();
    Integer lvTypId = leave.getLeaveType().getLeaveTypeId();

    if (mgrId==0){
        leave.setStatus(LeaveEnum.APPROVED);
        
        double balance=staffLeaveBalanceService.findStaffLeaveBalanceByStaffId(stfId,lvTypId);
        
        Double wdays=leaveService.checkWorkingDays(leave);
        double remainingBalance=balance-wdays;
        StaffLeaveBalance slvObj=staffLeaveBalanceService.findStaffLeaveBalanceObjectByStaffId(stfId,lvTypId);
        slvObj.setBalance(remainingBalance);
        
      }
    else {
        leave.setStatus(LeaveEnum.APPLIED);
      }
    double workingdays= leaveService.checkWorkingDays(leave);
    if(leave.getFromDate().equals(leave.getToDate())){
        if((leave.getFromDatePeriod().equals("AM") && leave.getToDatePeriod().equals("AM")) ||
        (leave.getFromDatePeriod().equals("PM") && leave.getToDatePeriod().equals("PM"))){
          
          leave.setDay(workingdays-0.5);
        }
        else{
          leave.setDay(workingdays);
        }
      }
      if(!leave.getFromDate().equals(leave.getToDate())){
        if (leave.getFromDatePeriod().equals("FullDay") && leave.getToDatePeriod().equals("FullDay")){
          leave.setDay(workingdays);
        }
        else if ((leave.getFromDatePeriod().equals("FullDay") && leave.getToDatePeriod().equals("AM")) ||
        leave.getFromDatePeriod().equals("FullDay") && leave.getToDatePeriod().equals("PM") ||
        leave.getFromDatePeriod().equals("AM") && leave.getToDatePeriod().equals("FullDay") ||
        leave.getFromDatePeriod().equals("PM") && leave.getToDatePeriod().equals("FullDay")){
          leave.setDay(workingdays-0.5);
        }
        else{
          leave.setDay(workingdays-1);
        }
      }
    leaveService.createLeave(leave);

    String stfName = usession.getStaff().getName();
    String mgrName = staffService.findStaffName(mgrId);
    String mgrEmail = staffService.findStaffEmailById(mgrId);
    EmailDetails ed = leaveService.newLeaveNotifMsg(stfName, stfId, mgrId, mgrName, mgrEmail);
    leaveService.sendNotifEmail(ed);
    
    String message = "New leave " + leave.getLeaveId() + " was successfully created.";
    System.out.println(message);
    
    return "redirect:/staff/leave/history";
  }
  

  @GetMapping("/leave/edit/{id}")
  public String editLeavePage(@PathVariable Integer id, Model model,HttpSession session) {
   UserSession usession = (UserSession) session.getAttribute("usession");
    Leaves leave = leaveService.findLeave(id);
    model.addAttribute("leave", leave);
    leave.setStaffId(usession.getStaff().getStaffId());
    List<LeaveType> leaveTypeNew = leaveTypeService.findAllLeaveType();
    model.addAttribute("leaveTypes", leaveTypeNew);
    
    return "leave-edit";
  }

  @PostMapping("/leave/edit/{id}")
  public String editLeave(@ModelAttribute @Valid Leaves leave, BindingResult result, @PathVariable Integer id,
      Model model, HttpSession session) throws LeaveNotFound {
   if (result.hasErrors()) {
    model.addAttribute("leaveTypes", leaveTypeService.findAllLeaveType());
       return "leave-edit";
     }
     
     UserSession usession = (UserSession) session.getAttribute("usession");
     Integer mgrId = usession.getStaff().getManagerId();
     Integer stfId = usession.getStaff().getStaffId();
     Integer lvTypId = leave.getLeaveType().getLeaveTypeId();

     if (mgrId==0){
         leave.setStatus(LeaveEnum.APPROVED);
         
         double balance=staffLeaveBalanceService.findStaffLeaveBalanceByStaffId(stfId,lvTypId);
         
         Double wdays=leaveService.checkWorkingDays(leave);
         double remainingBalance=balance-wdays;
         StaffLeaveBalance slvObj=staffLeaveBalanceService.findStaffLeaveBalanceObjectByStaffId(stfId,lvTypId);
         slvObj.setBalance(remainingBalance);
         
       }
     else {
         leave.setStatus(LeaveEnum.APPLIED);
       }
     double workingdays= leaveService.checkWorkingDays(leave);
     if(leave.getFromDate().equals(leave.getToDate())){
         if((leave.getFromDatePeriod().equals("AM") && leave.getToDatePeriod().equals("AM")) ||
         (leave.getFromDatePeriod().equals("PM") && leave.getToDatePeriod().equals("PM"))){
           
           leave.setDay(workingdays-0.5);
         }
         else{
           leave.setDay(workingdays);
         }
       }
       if(!leave.getFromDate().equals(leave.getToDate())){
         if (leave.getFromDatePeriod().equals("FullDay") && leave.getToDatePeriod().equals("FullDay")){
           leave.setDay(workingdays);
         }
         else if ((leave.getFromDatePeriod().equals("FullDay") && leave.getToDatePeriod().equals("AM")) ||
         leave.getFromDatePeriod().equals("FullDay") && leave.getToDatePeriod().equals("PM") ||
         leave.getFromDatePeriod().equals("AM") && leave.getToDatePeriod().equals("FullDay") ||
         leave.getFromDatePeriod().equals("PM") && leave.getToDatePeriod().equals("FullDay")){
           leave.setDay(workingdays-0.5);
         }
         else{
           leave.setDay(workingdays-1);
         }
       }
    
    leaveService.changeLeave(leave);
    
    return "redirect:/staff/leave/history";
  }

  @RequestMapping(value = "leave/view", method = RequestMethod.GET)
  public String allViewStaffLeaveInfo(HttpSession session, Model model) {

    UserSession usession = (UserSession) session.getAttribute("usession");

    System.out.println(usession.getStaff());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    LocalDate today = LocalDate.now();

    session.setAttribute("currDate", today);

   
    LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
    //本月的最后一天
    LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());

    List<Leaves> staffLeaves = leaveService.findAllLeavesByTime(firstday,lastDay);
    String currMonth = today.format(formatter);

    model.addAttribute("staffLeaves", staffLeaves);
    model.addAttribute("currentMonth", currMonth);

    return "leave-all-history";
  }

  @RequestMapping(value = "leave/view/{act}", method = RequestMethod.GET)
  public String allViewStaffLeaveInfo(@PathVariable int act, HttpSession session, Model model) {
    LocalDate currDate = (LocalDate) session.getAttribute("currDate");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

    LocalDate newDate;
    if (act == 0){
      newDate = currDate.minusMonths(1);
    } else {
      newDate = currDate.plusMonths(1);
    }

    LocalDate firstday = LocalDate.of(newDate.getYear(), newDate.getMonth(),1);
    LocalDate lastDay = newDate.with(TemporalAdjusters.lastDayOfMonth());

    String currMonth = newDate.format(formatter);

    session.setAttribute("currDate", newDate);

    List<Leaves> staffLeaves = leaveService.findAllLeavesByTime(firstday,lastDay);
    model.addAttribute("staffLeaves", staffLeaves);
    model.addAttribute("currentMonth", currMonth);

    return "leave-all-history";
  }
  @RequestMapping(value = "/leave/delete/{id}")
  public String deleteLeave(@PathVariable Integer id) throws LeaveNotFound {
    Leaves leave = leaveService.findLeave(id);
    Integer stfId = leave.getStaffId();
    LeaveEnum status = leave.getStatus();
    if (status == LeaveEnum.APPROVED) {
      leave.setStatus(LeaveEnum.CANCELLED);
      double balance=staffLeaveBalanceService.findStaffLeaveBalanceByStaffId(stfId, leave.getLeaveType().getLeaveTypeId());
      double remainingBalance=balance+leave.getDuration();

      StaffLeaveBalance slvObj=staffLeaveBalanceService.findStaffLeaveBalanceObjectByStaffId(stfId, leave.getLeaveType().getLeaveTypeId() );
      slvObj.setBalance(remainingBalance);
      
      
    }
    else {
      leave.setStatus(LeaveEnum.DELETED);
    }
    leaveService.changeLeave(leave);
   
    
    String message = "Leave " + leave.getLeaveId() + " was successfully deleted.";
    System.out.println(message);
    
    return "redirect:/staff/leave/history";
  }

}
