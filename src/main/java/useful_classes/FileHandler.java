package useful_classes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileHandler {
	boolean debug = false;
	private String filename;
	private String direction = "/home/pi/controller-ui/target/classes/";
	Logger logger = Logger.getLogger("MyLog");  
    Handler fh;  
	
	public void setFilename(String name) {
		filename = direction+name;
		//System.out.println("name: "+name);
		String filePath = getClass().getResource("/"+name).toString();
		if(filePath.startsWith("file:")){
			filename = filePath.substring(5);
			//System.out.println("full path: "+filePath);
			//System.out.println("filename: "+filename);
		} else if(filePath.startsWith("jar:file:")){
			filename = filePath.substring(9);
			filename = "/home/pi/controller-ui/target/classes/"+name;
			//System.out.println("full path: "+filePath);
			//System.out.println("filename: "+filename);
		}
	}
	
	public void setDirection(String name) {
		String base = "src/main/java/";
		//direction = base+name+"/";
		//direction = "";
		//if(name.equals("sav"))
		//	direction = "classes/";
	}

	public String getFilePath(){
		return filename;
	}
	
	public boolean ifExist() {
		//File myObj = new File(filename);
	    
		//return myObj.exists()
		 return new File(filename).exists();
	}
	  
	  public void writeFile(String text,boolean append) {
		  try {
	            FileWriter writer = new FileWriter(filename, append); //If true it will append the text, if false replace all the text
	            BufferedWriter bufferedWriter = new BufferedWriter(writer); 
	            bufferedWriter.write(text);
	            bufferedWriter.close();
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
	  }
	  
	  public void writeFile(char[] text,boolean append) {
		  try {
	            FileWriter writer = new FileWriter(filename, append); //If true it will append the text, if false replace all the text
	            BufferedWriter bufferedWriter = new BufferedWriter(writer); 
	            bufferedWriter.write(text);
	            bufferedWriter.close();
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
	  }
	  
	  public void writeFileln(String text,boolean append) {
		  try {
	            FileWriter writer = new FileWriter(filename, append); //If true it will append the text, if false replace all the text
	            BufferedWriter bufferedWriter = new BufferedWriter(writer); 
	            bufferedWriter.write(text);
	            bufferedWriter.write("\n");
	            bufferedWriter.close();
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
	  }
	  
	  public void writeFileln(char[] text,boolean append) {
		  try {
	            FileWriter writer = new FileWriter(filename, append); //If true it will append the text, if false replace all the text
	            BufferedWriter bufferedWriter = new BufferedWriter(writer); 
	            bufferedWriter.write(text);
	            bufferedWriter.write("\n");
	            bufferedWriter.close();
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
	  }
	  
	  public String readFile() {
		  String txt="";
		  try {
	            FileReader reader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(reader);
	           
	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                if(debug) System.out.println(line);
	                txt += line;
	            }
	            reader.close();
	 
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
		  return txt;
	  }
	  
	  public String[] readFileLine() {
		  String txt="";
		  String[] lines = null;
		  try {	
	            FileReader reader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(reader);
	           
	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                if(debug) System.out.println(line);
	                txt += line+"\n";
	            }
	            reader.close();
	            lines = txt.split("\n");
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
		  return lines;
	  }
	  
	  public String readFileLine(int line_number) {
		  String line="";

		  
		  try {
	            FileReader reader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(reader);
	           
	            
	            for(int i=0;i<line_number;i++){
	            	line = bufferedReader.readLine();
	                if(debug) System.out.println(line);
	            }

	             reader.close();
	                     
	 
	        } catch (IOException e) {
				LoggingTester("ERROR: En file Handler - writeFile" + String.valueOf( e));
	            e.printStackTrace();
	        }
		  return line;
	  }
	  public String[] searchFilesWithoutExtension(String filter) {
		  String[] nameList = new String[3];
		  FileFilter filtro = new FileFilter() {
			    @Override
			    public boolean accept(File arch) {
			    	boolean confirm = arch.getName().endsWith(filter);
			        return confirm; 
			    }
			};
		  
			File carpeta = new File(direction);
			File[] listado = carpeta.listFiles(filtro);
			
			if (listado == null || listado.length == 0) {
			    System.out.println("No hay elementos dentro de la carpeta actual");
			}
			else {
				nameList = new String[listado.length];
			    for (int i=0; i< listado.length; i++) {
			    	String name = listado[i].getName().toString().split("\\.")[0];
			    	//String name = listado[i].getName().toString();
			        //System.out.println(name);
			        nameList[i] = name;
			    }
			    
			}
		  return nameList;
	  }
	  public String[] searchFiles(String filter) {
		  String[] nameList = new String[3];
		  FileFilter filtro = new FileFilter() {
			    @Override
			    public boolean accept(File arch) {
			    	boolean confirm = arch.getName().endsWith(filter);
			        return confirm; 
			    }
			};
		  
			File carpeta = new File(direction);
			File[] listado = carpeta.listFiles(filtro);
			
			if (listado == null || listado.length == 0) {
			    System.out.println("No hay elementos dentro de la carpeta actual");
			}
			else {
				nameList = new String[listado.length];
			    for (int i=0; i< listado.length; i++) {
			    	String name = listado[i].getName().toString(); //.split("\\.")[0];
			    	//String name = listado[i].getName().toString();
			        //System.out.println(name);
			        nameList[i] = name;
			    }
			    
			}
		  return nameList;
	  }
	  
	  public String[] searchFiles() {
		String[] nameList = null;
		  
		File carpeta = new File(direction);
		File[] listado = carpeta.listFiles();
		
		if (listado == null || listado.length == 0) {
		    System.out.println("No hay elementos dentro de la carpeta actual");
		}
		else {
			
			nameList = new String[listado.length];
		    for (int i=0; i< listado.length; i++) {
		    	String name = listado[i].getName().toString();
		    	//String name = listado[i].getName().toString();
		        //System.out.println(name);
		        nameList[i] = name;
		    }
		    
		}
		  return nameList;
	  }

    public void setFormatter(SimpleFormatter formatter) {
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
