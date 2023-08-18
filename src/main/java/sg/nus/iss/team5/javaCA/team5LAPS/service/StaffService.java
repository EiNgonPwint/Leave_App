package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;

public interface StaffService {
    List<Staff> findStaffsByManager(int s);
    Staff findStaffById(int s);
    List<Staff> findAllStaffs();
    Staff findStaff(int sid);
    Staff createStaff(Staff stf);
    Staff changeStaff(Staff stf);
    void removeStaff(Staff stf);
    List<String> findAllManagerNames();
    List<Staff> findAllManagers();
    List<Staff> findSubordinates(int staffId);
    List<Integer> findAllStaffIDs();
    List<Integer> findStaffIDsExclusion(int id);
    String findStaffEmailById(int s);
    String findStaffName(int s);
    Staff findStaffByStaffName(String name);
  }

