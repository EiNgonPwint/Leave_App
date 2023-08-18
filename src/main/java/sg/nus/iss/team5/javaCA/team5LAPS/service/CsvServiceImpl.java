package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;

@Service
public class CsvServiceImpl implements CsvService{
	
	@Override
	  public void DownloadCsvLeaveFile(PrintWriter printWriter,List<Leaves> leaves) {
	    printWriter.write("Leave Report\n\n");
	    printWriter.write("Leave Id,Staff ID,Leave Type,Duration,From Date,To Date,Status,\n");
	    for(Leaves leave: leaves) {
	      printWriter.write(leave.getLeaveId()+","+leave.getStaffId()+","+leave.getLeaveType().getName()+","+leave.getDay() + ","+leave.getFromDate()+ ","+leave.getToDate()+ ","+leave.getStatus()+ "\n");
	    }
	}

	@Override
	public void DownloadCsvOtFile(PrintWriter printWriter,List<OvertimeRecord> otrecs){
		printWriter.write("OT Compensation Claim Report\n\n");
	    printWriter.write("Ot Id,Staff ID,Ot Date,Hours,Reason,Status,\n");
	    for(OvertimeRecord ot: otrecs) {
	      printWriter.write(ot.getOtId()+","+ot.getStaffId()+","+ot.getOtDate()+","+ot.getOtHour() + ","+ot.getReason()+ ","+ot.getStatus()+ "\n");
	    }
	}
}
