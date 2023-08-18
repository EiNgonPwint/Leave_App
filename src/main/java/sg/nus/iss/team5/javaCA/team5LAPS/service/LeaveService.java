package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.nus.iss.team5.javaCA.team5LAPS.model.EmailDetails;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;

public interface LeaveService {

  List<Leaves> findAllLeaves();

  Leaves findLeave(Integer lveid);

  Leaves createLeave(Leaves leave);

  Leaves changeLeave(Leaves leave);

  void removeLeave(Leaves leave);

  Double checkWorkingDays(Leaves leave) ;

  Page<Leaves> findLeavesBySID(int sid, Pageable pageable);
  
  List<Leaves> findLeavesByStfID(int sid);

  List<Leaves> findPendingLeavesBySID(int string);
  
  int getWeekendAndPublicHoliday(Leaves leave);
  boolean checkHoliday(LocalDate date);
  List<Leaves> findAllLeavesByTime(LocalDate fromDate, LocalDate toDate);

  void sendNotifEmail(EmailDetails ed) throws URISyntaxException;

  EmailDetails newLeaveNotifMsg(String stfName, int stfId, int mgrId, 
  String mgrName, String mgrEmail);

  EmailDetails newResultNotifMsg(String stfName, int stfId, 
	String stfEmail, String status);
 

}