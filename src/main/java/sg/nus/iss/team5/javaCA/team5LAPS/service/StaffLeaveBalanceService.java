package sg.nus.iss.team5.javaCA.team5LAPS.service;

import sg.nus.iss.team5.javaCA.team5LAPS.model.StaffLeaveBalance;

public interface StaffLeaveBalanceService {
	double findStaffLeaveBalanceByStaffId(int id,int leaveType);
//	 void updateBalance(int sid,int ltid, double bal);
	StaffLeaveBalance findStaffLeaveBalanceObjectByStaffId(int sid,int lid);
	
	StaffLeaveBalance createLeaveBalance(StaffLeaveBalance slb);
}
