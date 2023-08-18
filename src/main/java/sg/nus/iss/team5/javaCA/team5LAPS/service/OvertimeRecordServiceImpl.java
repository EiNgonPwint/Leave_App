package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.OvertimeRepository;

@Service
@Transactional (readOnly = true)
public class OvertimeRecordServiceImpl implements OvertimeRecordService{

	@Resource
	private OvertimeRepository overtimerecordRepository;
	@Override
	public List<OvertimeRecord> findAllOvertimeRecords() {
		// TODO Auto-generated method stub
		return overtimerecordRepository.findAll();
		
	}
	
//	@Override
//	public Optional<OvertimeRecord> findOvertimeRecord(int id) {
//		// TODO Auto-generated method stub
//		return overtimerecordRepository.findById(id);
//	}
	
	 @Override
	  public OvertimeRecord findOverTimeRecord(Integer id) {
	    return overtimerecordRepository.findById(id).orElse(null);
	  }
	
	@Transactional (readOnly = false)
	@Override
	public OvertimeRecord createOvertimeRecord(OvertimeRecord otr) {
		// TODO Auto-generated method stub
		return overtimerecordRepository.save(otr);
	}
	@Transactional (readOnly = false)
	@Override
	public OvertimeRecord editOvertimeRecord(OvertimeRecord otr) {
		// TODO Auto-generated method stub
		return overtimerecordRepository.save(otr);
	}
	@Transactional (readOnly = false)
	@Override
	public void deleteOvertimeRecord(int id) {
		// TODO Auto-generated method stub
		overtimerecordRepository.deleteById(id);
	}
	
	@Override
	  public List<OvertimeRecord> findOvertimeRecordsBySID(int sid) {
	    return overtimerecordRepository.findOvertimeRecordsBySID(sid);
	  }
	  

	  @Override
	  public List<OvertimeRecord> findPendingOvertimeRecordsBySID(int sid) {
	    return overtimerecordRepository.findPendingOvertimeRecordsBySID(sid);
	  }
	  
	  @Override
	  public Optional<OvertimeRecord> findot(int id)
	  {return overtimerecordRepository.findById(id);}

	
}

