package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;

import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;


public interface LeaveTypeService {
	 List<LeaveType> findAllLeaveType();
	 LeaveType createLeaveType(LeaveType leaveType);
	 LeaveType findLeaveType(int lid);
	 LeaveType changeLeaveType(LeaveType lts);
	 void removeLeaveType(LeaveType lts);
}
