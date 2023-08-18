package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.io.PrintWriter;

import java.util.*;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Leaves;
import sg.nus.iss.team5.javaCA.team5LAPS.model.OvertimeRecord;

public interface CsvService {
	public void DownloadCsvLeaveFile(PrintWriter printWriter,List<Leaves> leaves);
	public void DownloadCsvOtFile(PrintWriter printWriter,List<OvertimeRecord> otrecs);

}
