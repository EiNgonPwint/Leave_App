package sg.nus.iss.team5.javaCA.team5LAPS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
	 @Query("SELECT l FROM LeaveType l where l.leaveTypeId = :id")
	  LeaveType findById(@Param("id") int id);
}
