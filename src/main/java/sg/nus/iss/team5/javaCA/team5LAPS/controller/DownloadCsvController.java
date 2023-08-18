package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.model.UserSession;
import sg.nus.iss.team5.javaCA.team5LAPS.service.CsvService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.LeaveTypeService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.OvertimeRecordService;
import sg.nus.iss.team5.javaCA.team5LAPS.service.StaffService;

@Controller
@RequestMapping(value="/manager/download")
public class DownloadCsvController {
	@Autowired
	private CsvService csvService;
	
	@Autowired
	  private LeaveTypeService leaveTypeService;
	@Autowired
	  private LeaveService leaveService;

	  @Autowired
	  private StaffService staffService;

	  @Autowired
	  private OvertimeRecordService overtimerecord;
	
	
	  @GetMapping(value = "/lvreport")
	  public String staffLeaveHistory( Model model) {

	    List<LeaveType> leaveTypeNew = leaveTypeService.findAllLeaveType();
	    leaveTypeNew.add(new LeaveType("All",0.0,true));
	    model.addAttribute("leaveTypes", leaveTypeNew);
	    
	    model.addAttribute("leave", new Leaves());
	    return "report";
	  }

	  @PostMapping(value = "/lvreport")
	  public String staffLeaveHistory(@ModelAttribute @Valid Leaves leave,
		      Model model) {
		  List<Leaves> leaves=leaveService.findAllLeaves();
			
			
			List<Leaves> listLeaves= new ArrayList<Leaves>();
			for(Leaves lv: leaves) {
				if(lv.getFromDate().isAfter(leave.getFromDate().minusDays(1)) && lv.getToDate().isBefore(leave.getToDate().plusDays(1))
						&& leave.getLeaveType().getLeaveTypeId()==0) {
					listLeaves.add(lv);}
			
			else if(lv.getFromDate().isAfter(leave.getFromDate().minusDays(1)) && lv.getToDate().isBefore(leave.getToDate().plusDays(1))
					&& lv.getLeaveType().getLeaveTypeId()==leave.getLeaveType().getLeaveTypeId()) {
					listLeaves.add(lv);
					
				}
				
			}
		  
			model.addAttribute("staffLeaves", listLeaves);
			List<LeaveType> leaveTypeNew = leaveTypeService.findAllLeaveType();
			 model.addAttribute("leaveTypes", leaveTypeNew);
			    model.addAttribute("leave", new Leaves());
			    return "report-view";
	  }
	  
	  
	  	@PostMapping("/leaves.csv")
		public void DownloadCsvFile(HttpServletResponse response,@ModelAttribute @Valid Leaves leave) throws IOException{
			
		  List<Leaves> leaves=leaveService.findAllLeaves();
			
			
			List<Leaves> listLeaves= new ArrayList<Leaves>();
			for(Leaves lv: leaves) {
				if(lv.getFromDate().isAfter(leave.getFromDate().minusDays(1)) && lv.getToDate().isBefore(leave.getToDate().plusDays(1))
						&& leave.getLeaveType().getLeaveTypeId()==0) {
					listLeaves.add(lv);}
			
			else if(lv.getFromDate().isAfter(leave.getFromDate().minusDays(1)) && lv.getToDate().isBefore(leave.getToDate().plusDays(1))
					&& lv.getLeaveType().getLeaveTypeId()==leave.getLeaveType().getLeaveTypeId()) {
					listLeaves.add(lv);
					
				}
				
			}
		  
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; file=leaves.csv"); 
			csvService.DownloadCsvLeaveFile(response.getWriter(),listLeaves);
			
		}
	

	  	@GetMapping(value = "/otreport")
	    public String staffOtHistory( Model model) {

	      List<Staff> staffs = staffService.findAllStaffs();
	      staffs.add(0,new Staff("All"));
	      model.addAttribute("staffs", staffs);
	      
	      model.addAttribute("stf", new Staff());
	      return "report-ot";
	    }

	    @PostMapping(value = "/otreport")
	    public String staffOtHistory(@ModelAttribute @Valid Staff staff,
	          Model model) {

	      String name = staff.getName();
	      
	      List<OvertimeRecord> listOtrecs= new ArrayList<OvertimeRecord>();
	      if(name.equals("All")){
	        listOtrecs = overtimerecord.findAllOvertimeRecords();
	      }  
	      else{
	        Staff sttf = staffService.findStaffByStaffName(name);
	        Integer id = sttf.getStaffId();
	        listOtrecs = overtimerecord.findOvertimeRecordsBySID(id);
	      }
	        model.addAttribute("staffOts", listOtrecs);
	          return "report-ot-view";
	          
	    }
	    @PostMapping("/ots.csv")
	    public void DownloadCsvFile(HttpServletResponse response,@ModelAttribute @Valid Staff staff) throws IOException{
	      
	      String name = staff.getName();
	      List<OvertimeRecord> listOtrecs= new ArrayList<OvertimeRecord>();
	      if(name.equals("All")){
	        listOtrecs = overtimerecord.findAllOvertimeRecords();
	      }  
	      else{
	        Staff sttf = staffService.findStaffByStaffName(name);
	        Integer id = sttf.getStaffId();
	        listOtrecs = overtimerecord.findOvertimeRecordsBySID(id);
	      }
	      
	      response.setContentType("text/csv");
	      response.setHeader("Content-Disposition", "attachment; file=ots.csv"); 
	      csvService.DownloadCsvOtFile(response.getWriter(),listOtrecs);
	      
	    }
				
		
		  
}
	 



	