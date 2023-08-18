package sg.nus.iss.team5.javaCA.team5LAPS.model;


import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
    
@Entity
@Table(name = "otrecord")
public class OvertimeRecord {

  @Id
  @Column(name = "otid")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int otId;
  
  
  @Column(name = "staffid")
  private int staffId;
  
  @Column(name = "otdate")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate otDate;
  
  @Column(name = "othour")
  private Double otHour;
  


@Column(name = "reason")
  private String reason;
  
  @Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'CANCELLED', 'DELETED', 'UPDATED', 'REJECTED')")
  @Enumerated(EnumType.STRING)
  private LeaveEnum status;

  public OvertimeRecord() {}
  
  public OvertimeRecord(int otId) {
    this.otId = otId;
  }
  
  public OvertimeRecord(int staffId, LocalDate otDate,Double otHour, String reason, LeaveEnum status) {
    super();
    this.staffId = staffId;
    this.otDate = otDate;
    this.otHour = otHour;
    this.reason = reason;
    this.status = status;
  }

  public int getOtId() {
    return otId;
  }

  public void setOtId(int otId) {
    this.otId = otId;
  }

  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int string) {
    this.staffId = string;
  }

  public LocalDate getOtDate() {
    return otDate;
  }

  public void setOtDate(LocalDate otDate) {
    this.otDate = otDate;
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

  public LeaveEnum getStatus() {
    return status;
  }

  public void setStatus(LeaveEnum status) {
    this.status = status;
  }
  
  public Double getOtHour() {
	return otHour;
}

public void setOtHour(Double otHour) {
	this.otHour = otHour;
}

}
