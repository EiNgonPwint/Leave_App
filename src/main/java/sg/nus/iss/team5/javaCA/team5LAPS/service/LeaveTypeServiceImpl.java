package sg.nus.iss.team5.javaCA.team5LAPS.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;
import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.LeaveTypeRepository;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{

	@Resource
	private LeaveTypeRepository leaveTypeRepository;
	
	@Override
	public List<LeaveType> findAllLeaveType(){
		return leaveTypeRepository.findAll();
	}
	
	public LeaveType createLeaveType(LeaveType leaveType) {
		leaveType.setIsActive(true);
		return leaveTypeRepository.saveAndFlush(leaveType);
	}
	@Override
	@Transactional
	public LeaveType findLeaveType(int lid) {
	 return leaveTypeRepository.findById(lid);

	  }
	@Override
	  @Transactional
	  public LeaveType changeLeaveType(LeaveType lts) {
	    lts.setIsActive(true);
		return leaveTypeRepository.saveAndFlush(lts);
	  }
	@Override
	  @Transactional
	  public void removeLeaveType(LeaveType lts) {
		boolean status = lts.getIsActive();
		if (status == false){
			lts.setIsActive(true);
		}
		else {
			lts.setIsActive(false);
		}
		leaveTypeRepository.saveAndFlush(lts); 
		//leaveTypeRepository.delete(lts);
	  }
}
