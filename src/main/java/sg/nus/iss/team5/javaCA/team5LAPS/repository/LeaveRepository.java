package sg.nus.iss.team5.javaCA.team5LAPS.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;

public interface LeaveRepository extends JpaRepository<Leaves, Integer> {

  @Query("SELECT lv from Leaves lv WHERE lv.staffId = :sid")
  Page<Leaves> findLeavesBySID(@Param("sid") int sid, Pageable pageable);

  @Query("SELECT lv from Leaves lv WHERE lv.staffId = :sid")
  List<Leaves> findLeavesByStfID(@Param("sid") int sid);
  
  @Query("SELECT lv from Leaves lv WHERE lv.staffId = :sid AND (lv.status ='APPLIED' OR lv.status ='UPDATED') ")
  List<Leaves> findPendingLeavesBySID(@Param("sid") int sid);
  
  @Query(value = "SELECT * FROM leaves WHERE status = ?0", nativeQuery = true)
  List<Leaves> findPendingLeavesByStatus(String status);
  
  @Query("SELECT lv from Leaves lv where lv.fromDate between :beginDate and :endDate or lv.toDate between :beginDate and :endDate " +
          "and lv.status in (sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveEnum.APPLIED, sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveEnum.APPROVED," +
          "sg.nus.iss.team5.javaCA.team5LAPS.model.LeaveEnum.UPDATED)")
  List<Leaves> findAllLeave(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);
}
