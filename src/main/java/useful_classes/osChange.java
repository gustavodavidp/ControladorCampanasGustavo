package useful_classes;

import java.awt.Dimension;
import java.awt.Toolkit;

public class osChange {
	private String operativeSystem;
	public boolean imitateLinux = true;
	
	public osChange(){
		operativeSystem = System.getProperty("os.name");
	}
	
	public String getOS() {
		return operativeSystem;
	}
	
	public boolean ifWindows() {
		boolean win;
		if(operativeSystem.startsWith("Linux"))
			win = false;
		else
			win = true;
		
		return win;
	}
	
	public Dimension setDimension() {
		//Tamano de toda la pantalla
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				
		if(operativeSystem.startsWith("Windows") && imitateLinux)
			screenSize.setSize(1024,600);
		return screenSize;
	}

}
