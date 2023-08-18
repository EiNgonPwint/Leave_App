package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.StaffRepository;

@Service
public class StaffServiceImpl implements StaffService {

  @Resource
  private StaffRepository staffRepository;

  @Override
  @Transactional
  public List<Staff> findStaffsByManager(int s) {
    return staffRepository.findStaffsByManagerId(s);
  }

  @Override
  @Transactional
  public Staff findStaffById(int s) {
    return staffRepository.findStaffById(s);
  }

  @Override
  @Transactional
  public List<Staff> findAllStaffs() {
    return staffRepository.findAll();
  }

  @Override
  @Transactional
  public Staff findStaff(int sid) {
    return staffRepository.findById(sid).orElse(null);

  }

  @Override
  @Transactional
  public Staff createStaff(Staff stf) {
	//  stf.setIsActive(true);
    return staffRepository.saveAndFlush(stf);
  }

  @Override
  @Transactional
  public Staff changeStaff(Staff stf) {
	//  stf.setIsActive(true);
    return staffRepository.saveAndFlush(stf);
  }

  @Override
  @Transactional
  public void removeStaff(Staff stf) {
//    boolean status = stf.getIsActive();
//		if (status == false){
//			stf.setIsActive(true);
//		}
//		else {
//			stf.setIsActive(false);
//		}
//    staffRepository.saveAndFlush(stf);
//    staffRepository.delete(stf);
    staffRepository.updateUserStatusBySID(stf.getStaffId());
  }
  
  @Override
  @Transactional
  public List<String> findAllManagerNames() {
    return staffRepository.findAllManagerNames();
  }
  
  @Override
  @Transactional
  public List<Staff> findAllManagers() {
    return staffRepository.findAllManagers();
  }

  @Override
  @Transactional
  public List<Staff> findSubordinates(int staffId) {
    return staffRepository.findSubordinates(staffId);
  }

  @Override
  @Transactional
  public List<Integer> findAllStaffIDs() {
    return staffRepository.findAllStaffIDs();
  }
  
  @Override
  @Transactional
  public List<Integer> findStaffIDsExclusion(int id) {
	  return staffRepository.findStaffIDsExclusion(id);
  }

  @Override
  @Transactional
  public String findStaffEmailById(int s){
    return staffRepository.findStaffEmailById(s);
  }

  @Override
  @Transactional
  public String findStaffName(int s) {
    return staffRepository.findStaffName(s);
  }
  
  @Override
  @Transactional
  public Staff findStaffByStaffName(String name){
    return staffRepository.findStaffByStaffName(name);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}
