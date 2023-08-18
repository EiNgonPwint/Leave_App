package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.OvertimeRecordService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.StaffLeaveBalanceService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.StaffService;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Approve;
import sg.nus.iss.team5.javaCA.team5LAPS.model.EmailDetails;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveEnum;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.model.StaffLeaveBalance;
import sg.nus.iss.team5.javaCA.team5LAPS.model.UserSession;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.OvertimeRepository;

@Controller
@RequestMapping(value = "/manager")
public class ManagerLeaveController {

  @Autowired
  private LeaveService lService;
  
  @Autowired
  private StaffService staffService;
  
  @Autowired
  private StaffLeaveBalanceService staffLeaveBalanceService;
  
  @Autowired
  private OvertimeRecordService oService;
  
  @Autowired
  private OvertimeRepository oRepo;

  @InitBinder
  private void initBinder(WebDataBinder binder) {
    
  }
  
  @RequestMapping(value = "/pending")
  public String pendingApprovals(HttpSession session, Model model) {
    UserSession usession = (UserSession) session.getAttribute("usession");
    
    Map<Staff, List<Leaves>> subordinateToLeavesMap = new HashMap<>();
    for (Staff subordinate : usession.getSubordinates()) {
      List<Leaves> lvlist = lService.findPendingLeavesBySID(subordinate.getStaffId());
      subordinateToLeavesMap.put(subordinate, lvlist);
    }
    
    model.addAttribute("pendinghistory", subordinateToLeavesMap);
    
    Map<Staff, List<OvertimeRecord>> subordinateToOMap = new HashMap<>();
    for (Staff subordinate : usession.getSubordinates()) {
      List<OvertimeRecord> olist = oService.findPendingOvertimeRecordsBySID(subordinate.getStaffId());
      subordinateToOMap.put(subordinate,olist);
    }
    
    model.addAttribute("pendingOhistory", subordinateToOMap);
    return "manager-leave-pending";
  }
  
  @RequestMapping(value = "/subordinates-history")
  public String subordinatesHistory(HttpSession session, Model model) {
    UserSession usession = (UserSession) session.getAttribute("usession");
    
    Map<Staff, List<Leaves>> subordinateToLeavesMap = new HashMap<>();   
    for (Staff subordinate : usession.getSubordinates()) {
      subordinateToLeavesMap.put(subordinate, lService.findLeavesByStfID(subordinate.getStaffId()));
    }
    
    model.addAttribute("submap", subordinateToLeavesMap);
    
    return "manager-subordinate-leave-history";
    
    
  }
  
  @GetMapping("/leave/display/{id}")
  public String newDepartmentPage(@PathVariable int id, Model model) {
    Leaves leave = lService.findLeave(id);
    model.addAttribute("leave", leave);
    model.addAttribute("approve", new Approve());
    
    return "manager-leave-detail";
  }
  
  @GetMapping("/ot/display/{id}")
  public String OtPage(@PathVariable int id, Model model) {
	OvertimeRecord over = oService.findOverTimeRecord(id);
    model.addAttribute("over", over);
    model.addAttribute("approve", new Approve());
    
    return "manager-ot-detail";
  }
  
  @PostMapping("/leave/edit/{id}")
  public String approveOrRejectCourse(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
      @PathVariable Integer id) throws URISyntaxException {
    if (result.hasErrors())
      return "manager-leave-detail";
        
    Leaves lv = lService.findLeave(id);
    lv.setComment(approve.getComment());

    Integer stfId = lv.getStaffId();

    if (approve.getDecision().trim().equalsIgnoreCase(LeaveEnum.APPROVED.toString())) {
      lv.setStatus(LeaveEnum.APPROVED);
      double balance=staffLeaveBalanceService.findStaffLeaveBalanceByStaffId(stfId, lv.getLeaveType().getLeaveTypeId());
      double remainingBalance=balance-lv.getDay();

      StaffLeaveBalance slvObj=staffLeaveBalanceService.findStaffLeaveBalanceObjectByStaffId(stfId, lv.getLeaveType().getLeaveTypeId() );
      slvObj.setBalance(remainingBalance);

    } 
    else {
      lv.setStatus(LeaveEnum.REJECTED);
    }
    
    lService.changeLeave(lv);
    
    String status = lv.getStatus().toString();
    String stfName = staffService.findStaffName(stfId);
    String stfEmail = staffService.findStaffEmailById(stfId);
    EmailDetails ed = lService.newResultNotifMsg(stfName, stfId, stfEmail, status);
    lService.sendNotifEmail(ed);

    String message = "Leave was successfully updated.";
    System.out.println(message);
    
    return "redirect:/manager/pending";
  }
  
  @PostMapping("/ot/edit/{id}")
  public String approveOrRejectOvertime(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
      @PathVariable Integer id) {
    if (result.hasErrors())
      return "manager-ot-detail";
//    StaffLeaveBalance slb= staffLeaveBalanceService.
        
    OvertimeRecord over = oService.findOverTimeRecord(id);
    if (approve.getDecision().trim().equalsIgnoreCase(LeaveEnum.APPROVED.toString())) {
      over.setStatus(LeaveEnum.APPROVED);
      Staff s= staffService.findStaff(over.getStaffId());
      double balance=staffLeaveBalanceService.findStaffLeaveBalanceByStaffId(s.getStaffId(),3 );
      double remainingBalance=balance+((over.getOtHour()/4)*0.5);
//      s.getSlb().setBalance(remainingBalance);
      System.out.println(remainingBalance);
      StaffLeaveBalance slvObj=staffLeaveBalanceService.findStaffLeaveBalanceObjectByStaffId(s.getStaffId(),3 );
      slvObj.setBalance(remainingBalance);
      staffLeaveBalanceService.createLeaveBalance(slvObj);
      
     
    } else {
      over.setStatus(LeaveEnum.REJECTED);
      oRepo.saveAndFlush(over);
    }
    
    
    
    return "redirect:/manager/pending";
  }
  
}
