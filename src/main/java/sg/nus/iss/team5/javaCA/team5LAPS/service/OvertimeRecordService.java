package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;
import java.util.Optional;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;


public interface OvertimeRecordService {
	
	List<OvertimeRecord> findAllOvertimeRecords();
	OvertimeRecord findOverTimeRecord(Integer id);
	OvertimeRecord createOvertimeRecord(OvertimeRecord otr);
	OvertimeRecord editOvertimeRecord(OvertimeRecord otr);
	void deleteOvertimeRecord(int id);
	List<OvertimeRecord> findOvertimeRecordsBySID(int sid);
	List<OvertimeRecord> findPendingOvertimeRecordsBySID(int sid);
	Optional<OvertimeRecord> findot(int id);
	
	
	

}
