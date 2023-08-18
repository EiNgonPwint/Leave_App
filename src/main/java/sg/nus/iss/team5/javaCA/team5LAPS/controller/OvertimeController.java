package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;
import sg.nus.iss.team5.javaCA.team5LAPS.service.OvertimeRecordService;

@RestController
@RequestMapping("/api")
public class OvertimeController {
	@Autowired
	private OvertimeRecordService overtimeService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/ot")
	public List<OvertimeRecord> getAllOvertimeRecords(){
		
		return overtimeService.findAllOvertimeRecords();
		
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/otlist/{id}")
	public List<OvertimeRecord> getOvertimeRecordById(@PathVariable("id") int id)
	{
	
	
	  return overtimeService.findOvertimeRecordsBySID(id);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/ot")
	public ResponseEntity<OvertimeRecord> createOvertimeRecord(@RequestBody OvertimeRecord inOvertimeRecord) {
	try {
		OvertimeRecord retOvertimerecord =	overtimeService.createOvertimeRecord(inOvertimeRecord);
	
		return new ResponseEntity<OvertimeRecord>(retOvertimerecord, HttpStatus.CREATED);
	}
	catch(Exception e) {return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/ot/{id}")
	public ResponseEntity<OvertimeRecord> editOvertimeRecord(@PathVariable("id") int id, @RequestBody OvertimeRecord inOvertimeRecord)
	{ Optional<OvertimeRecord> optOvertimeRecord = overtimeService.findot(id);
	if (optOvertimeRecord.isPresent()) {
		OvertimeRecord overtimerecord=optOvertimeRecord.get();
		overtimerecord.setStaffId(inOvertimeRecord.getStaffId());
		overtimerecord.setOtDate(inOvertimeRecord.getOtDate());
		overtimerecord.setOtHour(inOvertimeRecord.getOtHour());
		overtimerecord.setReason(inOvertimeRecord.getReason());
		overtimerecord.setStatus(inOvertimeRecord.getStatus());
		
		OvertimeRecord updatedOvertimeRecord=overtimeService.editOvertimeRecord(overtimerecord);
		return new ResponseEntity<OvertimeRecord>(updatedOvertimeRecord,HttpStatus.OK);
	}
	else{return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/ot/{id}")
	public ResponseEntity<HttpStatus> deleteOvertimeRecord(@PathVariable("id") int id) {
		try {
		overtimeService.deleteOvertimeRecord(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/ot/{id}")
	public ResponseEntity<OvertimeRecord> getotid(@PathVariable("id") int id)
	{Optional<OvertimeRecord> or= overtimeService.findot(id);
	if(or.isPresent()) {OvertimeRecord o = or.get();
	return new ResponseEntity<OvertimeRecord>(o,HttpStatus.OK);}
	else{return new ResponseEntity<>(HttpStatus.NOT_FOUND);}}

}
