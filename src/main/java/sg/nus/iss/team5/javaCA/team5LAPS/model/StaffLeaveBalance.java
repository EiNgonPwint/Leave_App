package sg.nus.iss.team5.javaCA.team5LAPS.model;



import javax.persistence.*;

@Entity
@Table(name="staff_leave_balance")
public class StaffLeaveBalance{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="staff_leave_balance_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="leavetype_id")
	private LeaveType leavetype;
	
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	@Column(name="balance")
	private double balance;
	
	public StaffLeaveBalance() {}
	
	public StaffLeaveBalance(LeaveType leavetype,Staff staff, double balance) {
		
		this.leavetype=leavetype;
		this.staff=staff;
		this.balance=balance;
	}

	public LeaveType getLeavetype() {
		return leavetype;
	}

	public void setLeavetype(LeaveType leavetype) {
		this.leavetype = leavetype;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	
}