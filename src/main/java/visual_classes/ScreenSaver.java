package visual_classes;

import javax.swing.JPanel;
import useful_classes.osChange;
import java.awt.Color;
import java.awt.Dimension;

public class ScreenSaver extends JPanel {
	osChange os = new osChange();

	/**
	 * Create the panel.
	 */
	public ScreenSaver() {
		//Setting size parameters
		//Screen
		Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		
		setBounds(0,0,screenWidth,screenHeight);
		setBackground(Color.BLACK);
		
	}

}
