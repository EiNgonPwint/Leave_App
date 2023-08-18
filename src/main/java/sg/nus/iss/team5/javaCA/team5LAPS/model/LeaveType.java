package sg.nus.iss.team5.javaCA.team5LAPS.model;
import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="leavetype")
public class LeaveType implements Serializable{
	private static final long serialVersionUID=837878237429384928L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="leavetypeid")
	private int leaveTypeId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="balance")
	private double balance;
	
	@Column(name="isActive")
	private boolean isActive;
	
	public LeaveType() {}
	
public LeaveType(String name,double balance, boolean isActive) {
		
		this.setName(name);
		this.setBalance(balance);
		this.setIsActive(isActive);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	
	public boolean getIsActive(){
		return isActive;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	

	
}