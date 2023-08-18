package sg.nus.iss.team5.javaCA.team5LAPS.service;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import sg.nus.iss.team5.javaCA.team5LAPS.model.EmailDetails;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {
  
  @Resource
  private LeaveRepository leaveRepository;
  

  @Override
  public List<Leaves> findAllLeaves() {
    return leaveRepository.findAll();
  }


  @Override
  @Transactional
  public Leaves findLeave(Integer lveid) {
    return leaveRepository.findById(lveid).orElse(null);
  }

  @Override
  @Transactional
  public Leaves createLeave(Leaves leave) {
    return leaveRepository.saveAndFlush(leave);
  }

  @Override
  @Transactional
  public Leaves changeLeave(Leaves leave) {
    return leaveRepository.saveAndFlush(leave);
  }

  @Override
  @Transactional
  public void removeLeave(Leaves leave) {
    leaveRepository.delete(leave);
  }

  @Override
	public List<Leaves> findAllLeavesByTime(LocalDate fromDate,LocalDate toDate) {
		return leaveRepository.findAllLeave(fromDate,toDate);
	}
  
	@Override
	@Transactional
	public Page<Leaves> findLeavesBySID(int sid, Pageable pageable) {
	  return leaveRepository.findLeavesBySID(sid, pageable);
	}

  @Override
  @Transactional
  public List<Leaves> findLeavesByStfID(int sid) {
    return leaveRepository.findLeavesByStfID(sid);
  }
  

  @Override
  @Transactional
  public List<Leaves> findPendingLeavesBySID(int sid) {
    return leaveRepository.findPendingLeavesBySID(sid);
  }
  
  public String HolidayAPI(LocalDate date) {
		String year = String.valueOf(date.getYear());
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://public-holiday.p.rapidapi.com/" + year + "/SG"))
				.header("X-RapidAPI-Key", "a767e17492mshfb018b6e82f1297p1df624jsna92f6091cfb3")
				.header("X-RapidAPI-Host", "public-holiday.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		String response = client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).join();
		return response;

	}

	public Boolean CheckYear(LocalDate fromDate, LocalDate toDate) {
		if (fromDate.getYear() == toDate.getYear()) {
			return true;
		} else {
			return false;
		}
	}

	public long numberOfDays(LocalDate fromDate, LocalDate toDate) {
		long daysDiff = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
		return daysDiff;
	}

	public Double checkWorkingDays(Leaves leave) {
		LocalDate fromDate = leave.getFromDate();
		LocalDate toDate = leave.getToDate();
		long totalNoOfDays = numberOfDays(fromDate, toDate);
		int holsAndWeekendCounter = 0;
		String response = HolidayAPI(fromDate);
		if (CheckYear(fromDate, toDate) == true) {
			for (int i = 0; i < totalNoOfDays - 1; ++i) {
				if ((fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SATURDAY
						|| (fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SUNDAY
						|| response.contains(String.valueOf(fromDate.plusDays(i)))) {
					holsAndWeekendCounter++;
				}
			}
		} else {
			String response2 = HolidayAPI(toDate);
			for (int i = 0; i < totalNoOfDays - 1; ++i) {
				if ((fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SATURDAY
						|| (fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SUNDAY
						|| response.contains(String.valueOf(fromDate.plusDays(i)))
						|| response2.contains(String.valueOf(fromDate.plusDays(i)))) {
					holsAndWeekendCounter++;
				}
			}
		}

		double result = Double.valueOf(totalNoOfDays) - Double.valueOf(holsAndWeekendCounter);
		return result;
	}

	public int getWeekendAndPublicHoliday(Leaves leave) {
		LocalDate fromDate = leave.getFromDate();
		LocalDate toDate = leave.getToDate();
		long totalNoOfDays = numberOfDays(fromDate, toDate);
		int holsAndWeekendCounter = 0;
		String response = HolidayAPI(fromDate);
		
		if (CheckYear(fromDate, toDate) == true) {
			for (int i = 0; i < totalNoOfDays - 1; ++i) {
				if ((fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SATURDAY
						|| (fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SUNDAY
						|| response.contains(String.valueOf(fromDate.plusDays(i)))) {
					holsAndWeekendCounter++;
				}
			}
		} else {
			String response2 = HolidayAPI(toDate);
			for (int i = 0; i < totalNoOfDays - 1; ++i) {
				if ((fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SATURDAY
						|| (fromDate.plusDays(i)).getDayOfWeek() == DayOfWeek.SUNDAY
						|| response.contains(String.valueOf(fromDate.plusDays(i)))
						|| response2.contains(String.valueOf(fromDate.plusDays(i)))) {
					holsAndWeekendCounter++;
				}
			}
		}

		return holsAndWeekendCounter++;
	}
	
	public boolean checkHoliday(LocalDate date) {
		
		String response1 = HolidayAPI(date);
		
		if(response1.contains(String.valueOf(date)) || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return true;
		}
		return false;
	}

	@Override
	public void sendNotifEmail(EmailDetails ed) throws URISyntaxException {
	  // HttpHeaders headers = new HttpHeaders(null);
	  // headers.setContentType(MediaType.APPLICATION_JSON);
	
	  URI uri = new URI("http://localhost:8080/sendMail");
		
	  HttpEntity<EmailDetails> httpEntity = new HttpEntity<>(ed, null);
	
	  RestTemplate restTemplate = new RestTemplate();
	  String s = restTemplate.postForObject(uri, httpEntity, String.class);
	  System.out.println(s);		
	}

	@Override
	public EmailDetails newLeaveNotifMsg(String stfName, int stfId, int mgrId, 
	String mgrName, String mgrEmail) {
	  
	  EmailDetails ed1 = new EmailDetails();
	  String link = "http://localhost:8080";
	  	ed1.setRecipient(mgrEmail);
		ed1.setMsgBody("Dear "+ mgrName + " (StaffId: "+mgrId+"),\n\n "+stfName+" (StaffId: "+stfId+") submitted a new leave application. "+
		"\n\n Login at "+ link +" to view more details or approve/reject the leave application."+
		" \n\n Thank you!"+
		"\n\n This is a system generated email. If you require assistance, please contact system admin.");
		ed1.setSubject("New Staff Leave Application");
	  	return ed1;		
	}
  
	@Override
	public EmailDetails newResultNotifMsg(String stfName, int stfId, 
	String stfEmail, String status) {

	  // String link = "http://localhost:8080/manager/leave/display/"+lvId;
	  EmailDetails ed2 = new EmailDetails();
	  String link = "http://localhost:8080";
	  ed2.setRecipient(stfEmail);
	  ed2.setMsgBody("Dear "+ stfName + " (StaffId: "+stfId+"),"+
	  "\n\n Your leave application has been "+status+"."+
	  "\n\n Login at "+ link +" to view more details."+
	  "\n\n Thank you!"+
	  "\n\n This is a system generated email. If you require assistance, please contact system admin.");
	  ed2.setSubject("New Leave Application Outcome");
	  return ed2;		
	}
}