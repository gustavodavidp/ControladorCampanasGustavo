package useful_classes;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExecutionHandler {
	private GpioComm gpio = new GpioComm();
	private String[] executionLines;
	private Pattern toquePattern = Pattern.compile("([ABC])(\\d{6})");
	private Pattern bandeoPattern = Pattern.compile("([ABC])(\\d{6})#(\\d{6})");
	private boolean isToques;
	private ArrayList<Timer> timer = new ArrayList();
	private int executionDuration;
	private int extraDuration = 200;
	private boolean onExecution = false;
	private boolean onClockPulse = false;
	private boolean onBacklight = false;
	private boolean onGround = false;
	Logger logger = Logger.getLogger("MyLog");  
    Handler fh;  

	public ExecutionHandler(){
		gpio.initAll();
		//ground(true);
	}
	private void setExecutionLines(String[] executionLines){
		this.executionLines = executionLines;
	}
	
	private void defineType() {
		isToques = executionLines[0].equals("t");
	}
	
	private void newNote(String note,long delay,int duration) {

		try {
			TimerTask task = new TimerTask() {
				public void run() {
					gpio.setPulse(note,duration);
				}
			};
			timer.add(new Timer("Timer"));
			timer.get(timer.size()-1).schedule(task, delay);
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - newNote 1" + String.valueOf( e));
		}
		
	}
	
	private void newNote(String note,long delay) {
		int duration = 1000;
		try {
			TimerTask task = new TimerTask() {
				public void run() {
					gpio.setPulse(note,duration);
				}
			};
			timer.add(new Timer("Timer"));
			timer.get(timer.size()-1).schedule(task, delay);
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - newNote" + String.valueOf( e));
		}
		
	}
	
	public void stopExecution() {
		try {
			if(timer.size() > 0) {
				timer.forEach((t) -> t.cancel());
				timer.forEach((t) -> t.purge());
			}
			if (isToques){
				gpio.setLow("TA");
				gpio.setLow("TB");
				gpio.setLow("TC");
			} else {
				gpio.setLow("BA");
				gpio.setLow("BB");
				gpio.setLow("BC");
			}
			onExecution = false;
			ground(false);
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - stopExecution" + String.valueOf( e));
		}
		
	}

	public void executionHasFinished(){
		onExecution = false;
		ground(false);
	}
	
	public int playExecution(String[] executionLines) {
		onExecution = true;
		executionDuration = 0;
		ground(true);
		setExecutionLines(executionLines);
		defineType();

		try {
			if(isToques)
				playToques();
			else
				playBandeo();
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - playExecution" + String.valueOf( e));
		}
		
		return executionDuration+extraDuration;
	}

	private void playToques(){
		char executionType = 'T';
		int delay = 0;
		try {
			for(int i=1;i<executionLines.length-1;i++) {
				Matcher match = toquePattern.matcher(executionLines[i]);
				match.find();
				int mili = Integer.parseInt(match.group(2));
				delay += mili;
				String note = executionType + match.group(1);
				newNote(note,delay);
			}
			executionDuration = delay + 1000;
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - playToques" + String.valueOf( e));
		}
		
	}

	private void playBandeo(){
		char executionType = 'B';
		int delay = 0;
		try {
			for(int i=1;i<executionLines.length-1;i++) {
				Matcher match = bandeoPattern.matcher(executionLines[i]);
				match.find();
				int mili = Integer.parseInt(match.group(2));
				int duration = Integer.parseInt(match.group(3));
				delay += mili;
				String note = executionType + match.group(1);
				newNote(note,delay,duration);
				executionDuration = Math.max(executionDuration,delay + duration);
			}
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - playBandeo" + String.valueOf( e));
		}
		
	}
	
	public void clockPulseA() {
		try {
			onClockPulse = true;
			ground(true);
			gpio.setPulse("RA",1000);
			onClockPulse = false;
			ground(false);
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - clockPulseA" + String.valueOf( e));
		}
		
	}
	
	public void clockPulseB(int duration) {
		try {
			onClockPulse = true;
			ground(true);
			gpio.setPulse("RB",duration);
			onClockPulse = false;
			ground(false);
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - clockPulseB" + String.valueOf( e));
		}
		
	}
	
	public void carrillon(boolean state) {
		try {
			if(state) {
				onExecution = true;
				ground(true);
				gpio.setHigh("CARRILLON");
			} else {
				onExecution = false;
				ground(false);
				gpio.setLow("CARRILLON");
			}
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - carrillon" + String.valueOf( e));
		}
		
	}
	
	private void ground(boolean state) {
		try {
			if(state) {
				if(!onGround){
					gpio.setHigh("GND");
					onGround = true;
				}
			} else {
				if(!(onExecution || onClockPulse || onBacklight)){
					gpio.setLow("GND");
					onGround = false;
				}
			}
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - backgroundlight" + String.valueOf( e));
		}
		
	}
	
	public void backlight(boolean state) {
		try {
			if(state) {
				onBacklight = true;
				ground(true);
				gpio.setHigh("BACKLIGHT");
			} else {
				gpio.setLow("BACKLIGHT");
				onBacklight = false;
				ground(false);
			}
		} catch (Exception e) {
			LoggingTester("ERROR: En Execution Handler - backlight" + String.valueOf( e));
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
