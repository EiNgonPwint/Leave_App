package sg.nus.iss.team5.javaCA.team5LAPS.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;


public interface OvertimeRepository extends JpaRepository<OvertimeRecord,Integer>{
	
	 @Query("SELECT o from OvertimeRecord o WHERE o.staffId = :sid")
	  List<OvertimeRecord> findOvertimeRecordsBySID(@Param("sid") int sid);
	  
	  @Query("SELECT o from OvertimeRecord o WHERE o.staffId = :sid AND (o.status ='APPLIED' OR o.status ='UPDATED') ")
	  List<OvertimeRecord> findPendingOvertimeRecordsBySID(@Param("sid") int sid);
	  
	  @Query(value = "SELECT * FROM OvertimeRecord WHERE status = ?0", nativeQuery = true)
	  List<OvertimeRecord> findPendingOvertimeRecordsByStatus(String status);
}

