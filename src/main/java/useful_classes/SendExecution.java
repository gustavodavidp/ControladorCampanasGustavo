package useful_classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import visual_classes.MainPane;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;
import java.util.logging.Level;

public class SendExecution {
	MainPane main;
	public ArrayList<String> scheduledExecutionList;
	FileHandler fl = new FileHandler();
	FileHandler prevFl = new FileHandler();
	public boolean playSong = false;
	public boolean bellExecution = false;
	String executionToSend;
	String extension;
	Player musicFilePlayer;
	Timer timer;
	Timer executionTimer;
	public boolean okMessage = false;
	ExecutionDurationHandler executionDurationHandler;
	public boolean playPrev = true;
	ExecutionHandler executionHandler = new ExecutionHandler();
	osChange os = new osChange();
	Logger logger = Logger.getLogger("MyLog");  
    Handler fh;  

	public SendExecution(){
		fl.setDirection("sav");
		prevFl.setDirection("files");
		prevFl.setFilename("Ángelus.mp3");
	}
	
	public void setScheduledExecutionList(ArrayList<String> executionList) {
		this.scheduledExecutionList = executionList;
	}
	
	private void playMelodiasByPhone(int melodyNumber) {
		//Modificar cuando se habilite esta función
		FileHandler melodies = new FileHandler();
		melodies.setDirection("files");
		String[] files = melodies.searchFiles(".mp3");

		try {
			if(melodyNumber == 0) {
				stopSong();
			}
			else if(melodyNumber <= files.length && !playSong) {
				String melodiaName = files[melodyNumber-1];
				try {
					prepareForExecution(melodiaName);
				} catch (FileNotFoundException | JavaLayerException e) {
					// TODO Auto-generated catch block
					LoggingTester("ERROR: En MelodiasByPhone" + String.valueOf( e));
					e.printStackTrace();
				}
			}
			else {
				System.out.println("no hay suficientes");
				LoggingTester("ERROR: No hay melodias");
			}
		} catch (Exception e) {
			LoggingTester("ERROR: playMelodiasByPhone" + String.valueOf( e));
		}
		
	}
	
	public void compareDateStrings(String actualDateHour) throws FileNotFoundException, JavaLayerException {
		boolean coincide = false;
		boolean dateCoincide;
		boolean dayCoincide;
		boolean hourCoincide;
		//System.out.println("It is in compareDateStrings");
		try {
			for(int i=0;i<scheduledExecutionList.size();i++) {
				String[] scheduledParts = scheduledExecutionList.get(i).split(";");
				String[] actualDateHourParts = actualDateHour.split(";");
				boolean isDate = scheduledParts[0].contains("-");
				if(isDate) {
					dateCoincide = scheduledParts[0].equals(actualDateHourParts[1]);
					hourCoincide = scheduledParts[1].equals(actualDateHourParts[2]);
					coincide = dateCoincide && hourCoincide;
					LoggingTester("IsDate True en el compareDateString");
				}
				else {
					dayCoincide = scheduledParts[0].contains(actualDateHourParts[0]);
					hourCoincide = scheduledParts[1].equals(actualDateHourParts[2]);
					coincide = dayCoincide && hourCoincide;
					LoggingTester("isDate false en el compareDateString");
				}
				if(coincide) {
					prepareForExecution(scheduledParts[2]);
					if(isDate) deleteExecution(i);
					coincide=false;
					LoggingTester("Tomó el break en el compareDateString");
					break;
				}
				else{
					LoggingTester("Se fue por el else en el compareDateString");
				}
			}
			LoggingTester("Terminó de ejecutar en el compareDateStrings.");
		} catch (Exception e) {
			LoggingTester("ERROR: startExecution" + String.valueOf( e));
		}
		
	}
	
	public void  prepareForExecution(String executionName) throws FileNotFoundException, JavaLayerException {
		executionToSend = executionName;
		//("To execute: "+executionToSend);
		setExtension();
		setDirection();
		setFilename();
		startExecution();
	}
	
	public void setMainPane(MainPane main) {
		this.main = main;
	}
	
	private void setExtension(){
		extension = executionToSend.split("\\.")[1];
	}

	private void setDirection(){
		fl.setDirection("files");
	}

	private void setFilename(){
		fl.setFilename(executionToSend);
		//System.out.println("Filename: "+executionToSend);
	}

	private void startExecution() throws FileNotFoundException, JavaLayerException{
		main.principalPane.placeBtns(true);
		try {
			if(!playSong && !bellExecution)
			if(extension.equals("mp3")){
				//stopSong();
				//if(!playSong)
					playSong();
			} else {
				//System.out.println("Its gonna send to Arduino");
				//if(!bellExecution) {
					bellExecution = true;
					if(main.principalPane.playPrev) {
						playPrevSong();
						LoggingTester("Ejecución: se corrió el star execution en el playPrev");
					} else {
						LoggingTester("Ejecución: se corrió el star execution en el else");
						playExecution();
					}
				//}
			}
		} catch (Exception e) {
			LoggingTester("ERROR: startExecution" + String.valueOf( e));
		}
		
	}

	private void deleteExecution(int index) {

		try {
			fl.setDirection("sav");
			switch(extension){
				case "mp3":
					fl.setFilename("melodias.int");
					break;
				case "toc":
					fl.setFilename("toques.int");
					break;
				case "sec":
					fl.setFilename("secuencias.int");
					break;
			}
			String sheduledExecution = scheduledExecutionList.get(index);
			String[] fileLines;
			
			fileLines = fl.readFileLine();
			fl.writeFile("",false);
			for(String line: fileLines) {
				if(!line.equals(sheduledExecution)) {
					fl.writeFileln(line,true);
				}
			}
			main.principalPane.fillNameList();
			main.principalPane.reset(false);
			main.principalPane.repaint();
		} catch (Exception e) {
			LoggingTester("ERROR: deleteExecution" + String.valueOf( e));
		}
		
	}
		
	private void playExecution() {
		try {
			String[] fileLines;
			int duration;
			fileLines = fl.readFileLine();
			duration = executionHandler.playExecution(fileLines);
			executionFinished(duration);
			LoggingTester("ERROR: " + String.valueOf(duration));
		} catch (Exception e) {
			LoggingTester("ERROR: playExecution" + String.valueOf( e));
		}
		
	}
	
	public void playSong() throws FileNotFoundException, JavaLayerException {
		FileInputStream relative = new FileInputStream(fl.getFilePath());
		musicFilePlayer = new Player(relative);
		playSong = true;
		executionHandler.carrillon(true);
	      new Thread() {
	          public void run() {
					try {						
						while(playSong) {
							musicFilePlayer.play(1);
							if(musicFilePlayer.isComplete())
								stopSong();
						}	
					} catch (JavaLayerException e) {
						LoggingTester("ERROR: " + String.valueOf( e));
					   	e.printStackTrace();
					   
					}
	          }
	       }.start();      
	}
	
	public void playPrevSong() throws FileNotFoundException, JavaLayerException {
		FileInputStream relative = new FileInputStream(prevFl.getFilePath());
		musicFilePlayer = new Player(relative);
		playSong = true;
		executionHandler.carrillon(true);
	      new Thread() {
	          public void run() {
					try {						
						while(playSong) {
							musicFilePlayer.play(1);
							if(musicFilePlayer.isComplete())
								finishPrevSong();
						}	
					} catch (JavaLayerException e) {
						LoggingTester("ERROR: " + String.valueOf( e));
					   	e.printStackTrace();
					   
					}
	          }
	       }.start();      

		LoggingTester("Clase playPrevSong: " + String.valueOf( playSong));
	}
	
	public void stopBellExecution() {
		if(bellExecution) {
			bellExecution = false;
        	main.principalPane.placeBtns(false);
        	executionHandler.stopExecution();
		}
	}

	public void executionFinished(){
		bellExecution = false;
        main.principalPane.placeBtns(false);
		executionHandler.executionHasFinished();
	}
	
	public void executionFinished(long mili) {
		try {
			TimerTask task = new TimerTask() {
	        public void run() {
				bellExecution = false;
        		main.principalPane.placeBtns(false);
				executionHandler.executionHasFinished();
				}
			};
			timer = new Timer("Timer");
			
			long delay = mili;
			timer.schedule(task, delay);
			LoggingTester("Clase executionFinished: " + String.valueOf( timer) + "- Variable delay: " + String.valueOf( delay));
		} catch (Exception e) {
			LoggingTester("ERROR: executionFinished " + String.valueOf( e));
		}
	    

		
	}
	
	public void clockPulseA() {
		executionHandler.clockPulseA();
	}
	
	public void backlightOn() {
		executionHandler.backlight(true);
	}
	
	public void backlightOff() {
		executionHandler.backlight(false);
	}
	
	public void stopSong() {
		try {
			if(playSong) {
				playSong = false;
				musicFilePlayer.close();
				main.principalPane.placeBtns(false);
				executionHandler.carrillon(false);
			}
		} catch (Exception e) {
			LoggingTester("Clase stopSong: " + String.valueOf( playSong));
		}
	}
	
	public void finishPrevSong() {
		//System.out.println("entré ");
		try {
			System.out.print(playSong);
			if(playSong) {
				playSong = false;
				musicFilePlayer.close();
				executionHandler.carrillon(false);
				playExecution();
				//main.principalPane.placeBtns(false);
			}
		} catch (Exception e) {
			LoggingTester("Clase finishPrevSong: " + String.valueOf(playSong));
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