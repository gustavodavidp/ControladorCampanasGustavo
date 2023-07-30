package useful_classes;

import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecutionDurationHandler {
	private String[] exectutionLines;
	private int executionMili = 0;
	private Pattern toquePattern = Pattern.compile("[ABC](\\d\\d\\d\\d\\d\\d)");
	private Pattern bandeoPattern = Pattern.compile("[ABC](\\d{6})#(\\d{6})");
	Logger logger = Logger.getLogger("MyLog");  
    Handler fh;  
	
	void setExecutionLines(String[] exectutionLines){
		this.exectutionLines = exectutionLines;
	}
	
	int getDuration() {
		try {
			executionMili = 0;
			if(exectutionLines[0].contains("t")) 
				setDurationOnToques();
			else if(exectutionLines[0].contains("b")) 
				setDurationOnBandeos();
			
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution duration Handler - setDurationOnToques" + String.valueOf( e));
		}
		return executionMili;
	}
	
	void setDurationOnToques() {
		try {
			for(int i=1;i<exectutionLines.length-1;i++) {
			Matcher match = toquePattern.matcher(exectutionLines[i]);
			match.find();
			int mili = Integer.parseInt(match.group(1));
			executionMili += mili;
		}
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution duration Handler - setDurationOnToques" + String.valueOf( e));
		}
		
	}
	
	void setDurationOnBandeos() {
		try {
			int aux = 0;
			for(int i=1;i<exectutionLines.length-1;i++) {
				Matcher match = bandeoPattern.matcher(exectutionLines[i]);
				match.find();
				int miliWait = Integer.parseInt(match.group(1));
				int miliDuration = Integer.parseInt(match.group(2));
				aux += miliWait;
				executionMili = Math.max(executionMili,aux + miliDuration);
			}
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution duration Handler - setDurationOnBandeos" + String.valueOf( e));
		}
		

		
	}
	public void LoggingTester(String message) {
		try {
			// This block configure the logger with handler and formatter  
			fh = new java.util.logging.FileHandler("MyLogFile.log");  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
	
			// the following statement is used to log any messages  
			logger.info(message);  
		} catch (Exception e) {
			e.printStackTrace();  
			logger.info(e.toString());  
		}
	}
	
}
