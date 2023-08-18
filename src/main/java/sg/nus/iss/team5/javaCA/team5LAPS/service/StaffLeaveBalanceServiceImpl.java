package sg.nus.iss.team5.javaCA.team5LAPS.service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import sg.nus.iss.team5.javaCA.team5LAPS.model.StaffLeaveBalance;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.StaffLeaveBalanceRepository;

@Service
public class StaffLeaveBalanceServiceImpl implements StaffLeaveBalanceService {

	@Resource
	private StaffLeaveBalanceRepository staffLeaveBalanceRepo;
	
	@Override
	public double findStaffLeaveBalanceByStaffId(int id,int leaveType) {
		// TODO Auto-generated method stub
		System.out.print("Heerrrrr----------"+id+"------"+leaveType);
		
		return staffLeaveBalanceRepo.findStaffLeaveBalanceByStaffId(id,leaveType);
		
		
	}
	
//	@Override
//	public void updateBalance(int sid,int ltid, double bal) {
////	staffLeaveBalanceRepo.updateBalance(sid,ltid,bal);
//	}
	@Override
	public StaffLeaveBalance findStaffLeaveBalanceObjectByStaffId(int sid,int lid) {
		return staffLeaveBalanceRepo.findStaffLeaveBalanceObjectByStaffId(sid,lid);
	}
	
	@Override
	@Transactional
	public StaffLeaveBalance createLeaveBalance(StaffLeaveBalance slb) {
		return staffLeaveBalanceRepo.saveAndFlush(slb);
	}

}
