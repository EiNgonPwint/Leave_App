package sg.nus.iss.team5.javaCA.team5LAPS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
  @Query("SELECT s FROM Staff s where s.staffId = :id")
  Staff findStaffById(@Param("id") int s);
  
  @Query("SELECT s FROM Staff s where s.managerId = :mgrid")
  List<Staff> findStaffsByManagerId(@Param("mgrid") int mgrid);
  
  @Query("SELECT DISTINCT m FROM Staff s, Staff m where s.managerId = m.staffId ")
  List<Staff> findAllManagers();

  @Query("SELECT DISTINCT m.name FROM Staff s, Staff m where s.managerId = m.staffId ")
  List<String> findAllManagerNames();
    
  @Query("SELECT DISTINCT s2 FROM Staff s1, Staff s2 WHERE s1.staffId = s2.managerId AND s1.staffId = :sid")
  List<Staff> findSubordinates(@Param("sid") int sid);
  
  @Query("SELECT DISTINCT s.staffId FROM Staff s")
  List<Integer> findAllStaffIDs();
  
  @Query("SELECT DISTINCT s.staffId FROM Staff s where s.staffId!= :id")
  List<Integer> findStaffIDsExclusion(@Param("id") int id);

  @Query("SELECT s.email FROM Staff s where s.staffId = :id")
  String findStaffEmailById(@Param("id") int s);

  @Query("SELECT s.name FROM Staff s where s.staffId = :id")
  String findStaffName(@Param("id") int s);
  
//change corresponding user status when delete staff
  @Modifying
  @Query("update User u set u.isActive=false where  u.staffId= :sid")
  void updateUserStatusBySID(@Param("sid") Integer staffId);

//display is active staff
  @Query("Select distinct s from Staff s,User u where s.staffId=u.staffId and u.isActive=true")
  List<Staff> findAll();
  
  @Query("SELECT s FROM Staff s where s.name = :name")
  Staff findStaffByStaffName(@Param("name") String name);
}