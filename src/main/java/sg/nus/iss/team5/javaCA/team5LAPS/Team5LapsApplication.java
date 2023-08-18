package sg.nus.iss.team5.javaCA.team5LAPS;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveEnum;
import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.model.StaffLeaveBalance;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Role;
import sg.nus.iss.team5.javaCA.team5LAPS.model.User;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.LeaveRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.LeaveTypeRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.OvertimeRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.StaffRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.RoleRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.StaffLeaveBalanceRepository;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.UserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Team5LapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team5LapsApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(RoleRepository roleRepository, 
                            StaffRepository staffRepository,
                            UserRepository userRepository,
                            LeaveRepository leaveRepository,
                            LeaveTypeRepository leaveTypeRepository,
                            StaffLeaveBalanceRepository slbr,
                            OvertimeRepository otr
			) {
    return (args) -> {
      // Add a few Roles
    	Role adminRole = roleRepository.save(new Role("admin", "Administrator", "System administrator",true));
        Role staffRole = roleRepository.save(new Role("staff", "Staff", "Staff members",true));
        Role managerRole = roleRepository.save(new Role("manager", "Manager", "Manager",true));
      
      // Add a few Staffs
      
      Staff s1=new Staff("Admin",0,"admin@gmail.com");
      Staff s2=new Staff("Esther Tan",0,"esther.iss.nus@gmail.com");
      Staff s3=new Staff("Nguyen Tri Tin",2,"arntminzaw@gmail.com");
      Staff s4=new Staff("Cher Wah",2,"pgoxmen@gmail.com");
     
      staffRepository.save(s1);
      staffRepository.save(s2);
      staffRepository.save(s3);
      staffRepository.save(s4);
     
      
      // Add a few Users
      User admin = new User(1,"admin", "password",s1.getStaffId(),true );
      User esther = new User(2,"esther", "password", s2.getStaffId(),true);
      User tin = new User(3,"tin", "password", s3.getStaffId(),true);
      User cherwah = new User(4,"cherwah", "password", s4.getStaffId(),true);
      
      
      admin.setRoleSet(Arrays.asList(adminRole));
      esther.setRoleSet(Arrays.asList(staffRole, managerRole));
      tin.setRoleSet(Arrays.asList(staffRole));
      cherwah.setRoleSet(Arrays.asList(staffRole));
      
      userRepository.saveAndFlush(admin);
      userRepository.saveAndFlush(esther);
      userRepository.saveAndFlush(tin);
      userRepository.saveAndFlush(cherwah);
      
      
      LeaveType annual=new LeaveType("Annual",14,true);
      LeaveType medical=new LeaveType("Medical",60,true);
      LeaveType compensation=new LeaveType("Compensation Leave",0,true);
      
      
      leaveTypeRepository.saveAndFlush(annual);
      leaveTypeRepository.saveAndFlush(medical);
      leaveTypeRepository.saveAndFlush(compensation);
      
      // Add a few leave applications
      Leaves leave1 = new Leaves();
      leave1.setLeaveType(annual);
      leave1.setFromDate(LocalDate.now());
      leave1.setToDate(LocalDate.now().plusDays(1));
      
      leave1.setWork("test");
      leave1.setReason("OOP is the best");
      leave1.setStaffId(cherwah.getStaffId());
      leave1.setStatus(LeaveEnum.APPLIED);
      leave1.setContact("1234");
      leave1.setDuration(2);
      leave1.setFromDatePeriod("FullDay");
      leave1.setToDatePeriod("FullDay");
      leave1.setDay(2.0);
    
      leaveRepository.saveAndFlush(leave1);
      
      Leaves leave2 = new Leaves();
      leave2.setLeaveType(medical);
      leave2.setFromDate(LocalDate.now());
      leave2.setToDate(LocalDate.now().plusDays(1));
     
      leave2.setWork("test");
      leave2.setReason("OOP is the best");
      leave2.setStaffId(tin.getStaffId());
      leave2.setStatus(LeaveEnum.APPLIED);
      leave2.setContact("0909090");
      leave2.setDuration(2);
      leave2.setFromDatePeriod("FullDay");
      leave2.setToDatePeriod("FullDay");
      leave2.setDay(2.0);
      
     leaveRepository.saveAndFlush(leave2);


     Leaves leave3 = new Leaves();
      leave3.setLeaveType(medical);
      leave3.setFromDate(LocalDate.now());
      leave3.setToDate(LocalDate.now().plusDays(1));
     
      leave3.setWork("test");
      leave3.setReason("OOP is the best");
      leave3.setStaffId(tin.getStaffId());
      leave3.setStatus(LeaveEnum.APPLIED);
      leave3.setContact("0909090");
      leave3.setDuration(2);
      leave3.setFromDatePeriod("FullDay");
      leave3.setToDatePeriod("FullDay");
      leave3.setDay(2.0);
      
     leaveRepository.saveAndFlush(leave3);

     Leaves leave4 = new Leaves();
     leave4.setLeaveType(annual);
     leave4.setFromDate(LocalDate.now());
     leave4.setToDate(LocalDate.now().plusDays(1));
    
     leave4.setWork("test");
     leave4.setReason("OOP is the best");
     leave4.setStaffId(tin.getStaffId());
     leave4.setStatus(LeaveEnum.APPLIED);
     leave4.setContact("0909090");
     leave4.setDuration(2);
     leave4.setFromDatePeriod("FullDay");
     leave4.setToDatePeriod("FullDay");
     leave4.setDay(2.0);
     
    leaveRepository.saveAndFlush(leave4);

    Leaves leave5 = new Leaves();
    leave5.setLeaveType(medical);
    leave5.setFromDate(LocalDate.now());
    leave5.setToDate(LocalDate.now().plusDays(1));
   
    leave5.setWork("test");
    leave5.setReason("OOP is the best");
    leave5.setStaffId(cherwah.getStaffId());
    leave5.setStatus(LeaveEnum.APPLIED);
    leave5.setContact("0909090");
    leave5.setDuration(2);
    leave5.setFromDatePeriod("FullDay");
    leave5.setToDatePeriod("FullDay");
    leave5.setDay(2.0);
    
   leaveRepository.saveAndFlush(leave5);

   Leaves leave6 = new Leaves();
   leave6.setLeaveType(annual);
   leave6.setFromDate(LocalDate.now());
   leave6.setToDate(LocalDate.now().plusDays(1));
  
   leave6.setWork("test");
   leave6.setReason("OOP is the best");
   leave6.setStaffId(cherwah.getStaffId());
   leave6.setStatus(LeaveEnum.APPLIED);
   leave6.setContact("0909090");
   leave6.setDuration(2);
   leave6.setFromDatePeriod("FullDay");
   leave6.setToDatePeriod("FullDay");
   leave6.setDay(2.0);
   
  leaveRepository.saveAndFlush(leave6);
     
     StaffLeaveBalance slb1=new StaffLeaveBalance();
     //slb1.setId(1);
     slb1.setLeavetype(medical);
     slb1.setStaff(s1);
     slb1.setBalance(14);
     slbr.saveAndFlush(slb1);
     
     StaffLeaveBalance slbl2 = new StaffLeaveBalance(); 
     //slbl2.setId(2);
     slbl2.setLeavetype(annual);
     slbl2.setStaff(s3);
     slbl2.setBalance(14.0);
      slbr.saveAndFlush(slbl2);
      
      
      StaffLeaveBalance slbl4 = new StaffLeaveBalance(); 
      //slbl4.setId(4);
      slbl4.setLeavetype(medical);
      slbl4.setStaff(s3);
      slbl4.setBalance(60.0);
       
       slbr.saveAndFlush(slbl4);
      
       
       StaffLeaveBalance slbl5 = new StaffLeaveBalance(); 
       //slbl5.setId(5);
       slbl5.setLeavetype(annual);
       slbl5.setStaff(s2);
       slbl5.setBalance(14.0);
        
        slbr.saveAndFlush(slbl5);
        
        StaffLeaveBalance slbl7 = new StaffLeaveBalance(); 
        //slbl7.setId(7);
        slbl7.setLeavetype(medical);
        slbl7.setStaff(s4);
        slbl7.setBalance(14.0);
         
         slbr.saveAndFlush(slbl7);
        
        
        StaffLeaveBalance slbl6 = new StaffLeaveBalance(); 
        //slbl6.setId(6);
        slbl6.setLeavetype(medical);
        slbl6.setStaff(s2);
        slbl6.setBalance(60.0);
         
         slbr.saveAndFlush(slbl6);
      
      StaffLeaveBalance slbl3 = new StaffLeaveBalance(); 
      //slbl3.setId(3);
      slbl3.setLeavetype(annual);
      slbl3.setStaff(s4);
      slbl3.setBalance(60);
       
       slbr.saveAndFlush(slbl3);
      
      
       StaffLeaveBalance slbl8 = new StaffLeaveBalance(); 
       //slbl3.setId(3);
       slbl8.setLeavetype(compensation);
       slbl8.setStaff(s3);
       slbl8.setBalance(5);
        
        slbr.saveAndFlush(slbl8);
        StaffLeaveBalance slbl9 = new StaffLeaveBalance(); 
        //slbl3.setId(3);
        slbl9.setLeavetype(compensation);
        slbl9.setStaff(s4);
        slbl9.setBalance(5);
         
         slbr.saveAndFlush(slbl9);

        OvertimeRecord ot1 = new OvertimeRecord();
        ot1.setStaffId(tin.getStaffId());
        ot1.setOtDate(LocalDate.now());
        ot1.setOtHour(8.0);
        ot1.setReason("test");
        ot1.setStatus(LeaveEnum.APPLIED);
        otr.saveAndFlush(ot1);

        OvertimeRecord ot2 = new OvertimeRecord();
        ot2.setStaffId(cherwah.getStaffId());
        ot2.setOtDate(LocalDate.now());
        ot2.setOtHour(8.0);
        ot2.setReason("test");
        ot2.setStatus(LeaveEnum.APPLIED);
        otr.saveAndFlush(ot2);

        OvertimeRecord ot3 = new OvertimeRecord();
        ot3.setStaffId(esther.getStaffId());
        ot3.setOtDate(LocalDate.now());
        ot3.setOtHour(8.0);
        ot3.setReason("test");
        ot3.setStatus(LeaveEnum.APPLIED);
        otr.saveAndFlush(ot3);

      
      
      
      
      
      
      
    };
	}
}