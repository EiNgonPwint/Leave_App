package sg.nus.iss.team5.javaCA.team5LAPS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.team5.javaCA.team5LAPS.model.StaffLeaveBalance;

@Repository
public interface StaffLeaveBalanceRepository extends JpaRepository<StaffLeaveBalance,String>{
	

	@Query("SELECT slb.balance FROM StaffLeaveBalance slb WHERE slb.staff.staffId =:id AND slb.leavetype.leaveTypeId =:leaveType")
	  double findStaffLeaveBalanceByStaffId(@Param("id") int id,@Param("leaveType") int leaveType);

	@Query("SELECT slb FROM StaffLeaveBalance slb where slb.staff.staffId =:sid AND slb.leavetype.leaveTypeId =:lid")
	StaffLeaveBalance findStaffLeaveBalanceObjectByStaffId(@Param("sid") int sid,@Param("lid") int lid);

}
