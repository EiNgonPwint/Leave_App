package sg.nus.iss.team5.javaCA.team5LAPS.model;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "leaves")
public class Leaves {

  @Id
  @Column(name = "leaveid")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int leaveId;
  
  
  @Column(name = "staffid")
  private int staffId;
  

//  @ManyToOne
//  @JoinColumn(name = "staffid")
//  private String staffId;
  
//  @Column(name = "leavetype")
//  private String leaveType;
  
  	@ManyToOne
	@JoinColumn(name = "leavetype_id")
   private LeaveType leaveType;
  
  @Column(name = "fromdate")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fromDate;
  
  @Column(name = "todate")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate toDate;
  
  @Column(name = "duration")
  private double duration;
  
  @Column(name = "reason")
  private String reason;
  
  @Column(name ="fromDatePeriod")
  private String fromDatePeriod;
  
  @Column(name="toDatePeriod")
  private String toDatePeriod;

  @Column(name = "workdissemination")
  private String work;
  
  @Column(name="contact")
  private String contact;
  
  @Column(name="comment")
  private String comment;
  
  @Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'CANCELLED', 'DELETED', 'UPDATED', 'REJECTED')")
  @Enumerated(EnumType.STRING)
  private LeaveEnum status;
  
  @Column(name = "workingdays")
	private Double day;

  public Leaves() {}
  
  public Leaves(int leaveId) {
    this.leaveId = leaveId;
  }
  
  public Leaves(int staffId, LeaveType leaveType, LocalDate fromDate, LocalDate toDate,
		  		String reason, String work, LeaveEnum status, 
		  		String contact, Double day, Double duration,String comment,String fromDatePeriod, String toDatePeriod) {
    super();
    this.staffId = staffId;
  this.leaveType = leaveType;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.reason = reason;
    this.work = work;
    this.status = status;
    this.contact=contact;
    this.day = day;
    this.duration = duration;
    this.comment=comment;
    this.fromDatePeriod=fromDatePeriod;
    this.toDatePeriod=toDatePeriod;
  }

  public int getLeaveId() {
    return leaveId;
  }

  public void setLeaveId(int leaveId) {
    this.leaveId = leaveId;
  }

  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int i) {
    this.staffId = i;
  }

  public LeaveType getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(LeaveType leaveType) {
    this.leaveType = leaveType;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  public void setFromDate(LocalDate fromDate) {
    this.fromDate = fromDate;
  }

  public LocalDate getToDate() {
    return toDate;
  }

  public void setToDate(LocalDate toDate) {
    this.toDate = toDate;
  }
  
  public double getDuration() {
		return duration;
	}

  public void setDuration(double duration) {
		this.duration = duration;
	}

//  public double getLeavePeriod() {
//    return leaveperiod;
//  }
//
//  public void setLeavePeriod(double leaveperiod) {
//    this.leaveperiod = leaveperiod;
//  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getWork() {
    return work;
  }

  public void setWork(String work) {
    this.work = work;
  }

  public LeaveEnum getStatus() {
    return status;
  }

  public void setStatus(LeaveEnum status) {
    this.status = status;
  }

public String getContact() {
	return contact;
}

public void setContact(String contact) {
	this.contact = contact;
}
public Double getDay() {
	return day;
}

public void setDay(Double day) {
	this.day = day;
}

public String getComment() {
	return comment;
}

public void setComment(String comment) {
	this.comment = comment;
}

public String getFromDatePeriod() {
	return fromDatePeriod;
}

public void setFromDatePeriod(String dayPeriod) {
	this.fromDatePeriod = dayPeriod;
}

public String getToDatePeriod() {
	return toDatePeriod;
}

public void setToDatePeriod(String toDatePeriod) {
	this.toDatePeriod = toDatePeriod;
}

}
