package visual_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;


import useful_classes.osChange;

/**
 * A simple virtual keyboard in the Brazilian ABNT2 layout.
 *
 * In order to use this class you must:
 *
 * 1. Create a new instance providing the size of the virtual keyboard; <br>
 * 2. Provide a text component that will be used to store the the keys typed
 * (this is performed with a separate call to setCurrentTextComponent; <br>
 * 3. Call the show method in order to show the virtual keyboard in a given
 * JFrame.
 *
 * @author Wilson de Carvalho
 */
public class VirtualNumberKeyboard  extends JPanel{
    int screenWidth;
    int screenHeight;
    int keyPanWidth;
    int keyPanHeight;
    int keyPanX;
    int keyPanY;
    int textSize = 50;
	private int rowY = 0;
	private int alpha = 255;
	private JLabel[] hourData = new JLabel[3];
	int select=0;
	private String dataBuffer="";
	private JPanel parent;

	osChange os = new osChange();
    /**
     * Private class for storing key specification.
     */
	//Special keys
	private final char LEFT_ARROW = '\u2190'; //u2190
	private final char RIGHT_ARROW = '\u2192'; //u2192
	private final char UP_ARROW = '\u2191';
	private final char DOWN_ARROW = '\u2193';
	private final char CLEAR = 'X'; //u2718
	private final char READY = '\u2713'; //u2714

	
    // First key row
    private char[] row1 = new char[]{
        '7','8','9',UP_ARROW
    };
    
    // Second key row
    private char[] row2 = new char[]{
            '4','5','6',DOWN_ARROW
    };

    // Third key row
    private char[] row3 = new char[]{
            '1','2','3',CLEAR
    };

    // Fourth key row
    private char[] row4 = new char[]{
    		LEFT_ARROW,'0',RIGHT_ARROW,READY
    };

    public VirtualNumberKeyboard() {
    	Dimension screenSize = os.setDimension();
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
		keyPanWidth = 400;
		keyPanHeight = 355;
		keyPanY = screenHeight - keyPanHeight;
		setBackground(new Color(0,0,0,200));
		setLayout(null);
    }
    
    public VirtualNumberKeyboard(int width,int height,int text) {
    	Dimension screenSize = os.setDimension();
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
		keyPanWidth = width;
		keyPanHeight = height;
		textSize = text;
		keyPanY = screenHeight - keyPanHeight;
		setBackground(new Color(0,0,0,200));
		setLayout(null);
    }

    public void setParent(SelectHourPanel parent) {
    	this.parent = parent;
    	this.hourData = parent.hourData;
        keyPanX = parent.getWidth()/2 - keyPanWidth/2;
        setBounds(keyPanX,keyPanY,keyPanWidth,keyPanHeight);
        Color keyColor = getBackground();
        select=0;
        add(initRow(row1, getSize(),keyColor));
        add(initRow(row2, getSize(),keyColor));
        add(initRow(row3, getSize(),keyColor));
        add(initRow(row4, getSize(),keyColor));
        
    }
    
    public void setParent(SelectDayHourPanel parent) {
    	this.parent = parent;
    	this.hourData = parent.hourData;
        keyPanX = parent.getWidth()/2 - keyPanWidth/2;
        setBounds(keyPanX,keyPanY,keyPanWidth,keyPanHeight);
        Color keyColor = getBackground();
        select=0;
        add(initRow(row1, getSize(),keyColor));
        add(initRow(row2, getSize(),keyColor));
        add(initRow(row3, getSize(),keyColor));
        add(initRow(row4, getSize(),keyColor));
        
    }
        
    public void setAlpha(int alpha) {
    	alpha = Math.max(0,alpha);
    	alpha = Math.min(255,alpha);
    	this.alpha = alpha;
    }
    
    public Color getColorWithAlpha(Color color) {
    	Color colorWithAlpha;
    	colorWithAlpha = new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha);
    	return colorWithAlpha;
    }

    private JPanel initRow(char[] keys, Dimension dimensions, Color panelColor) {
    	int outerButtonsGap = 10;
    	int innerButtonsGap = 10;
    	
        JPanel p = new JPanel();
        p.setLayout(null);
        int buttonWidth = (dimensions.width - outerButtonsGap*2 - innerButtonsGap*(keys.length-1) ) / keys.length;
        int buttonHeight = (dimensions.height - outerButtonsGap*2 - innerButtonsGap*3) / 4; // number of rows
        int compensationButtonX = Math.round(dimensions.width-outerButtonsGap*2)/2-(buttonWidth*keys.length+innerButtonsGap*(keys.length-1))/2;
        for (int i = 0; i < keys.length; ++i) {
            char key = keys[i];
            JButton button;
            button = new JButton(String.valueOf(key));
			button.setFont(new Font("Arial",Font.PLAIN,20));
            int lineThickness = 2;
            button.setBorder(new MatteBorder(lineThickness, lineThickness, lineThickness, lineThickness, (Color) Color.WHITE));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(0,0,0,255));
            button.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mousePressed(MouseEvent e) {
    				button.setBackground(new Color(255,255,255));
    			}
    			@Override
    			public void mouseReleased(MouseEvent e) {
    				button.setBackground(new Color(0,0,0));
    			}
    		});
            
            button.setFont(new Font("Tahoma", Font.PLAIN, textSize));
            int buttonX = compensationButtonX+i*(buttonWidth+innerButtonsGap);
            button.setBounds(buttonX,0,buttonWidth, buttonHeight);
            button.addActionListener(e -> actionListener(key));
            p.add(button);
        }
        int pX = outerButtonsGap;
        int pY = outerButtonsGap+rowY;
        int pWidth = dimensions.width-outerButtonsGap*2;
        int pHeight = buttonHeight;
        p.setBounds(pX,pY,pWidth,pHeight);
       // p.setBackground(panelColor);
        p.setOpaque(false);
        rowY += pHeight + innerButtonsGap;
        return p;
    }

    private void actionListener(char key) {
    	int numData;
    	if(isNumeric(key)) {
	    	dataBuffer += key;
	    	writeData();
    	}
    	else {
    		switch(key) {
    		case UP_ARROW:
    			numData = select<2? Integer.parseInt(hourData[select].getText()):0;
    			stepWrite(numData,true);
    			break;
    		case DOWN_ARROW:
    			numData = select<2? Integer.parseInt(hourData[select].getText()):0;
    			stepWrite(numData,false);
    			break;
    		case LEFT_ARROW:
    			select=Math.max(select-1,0);
    			dataBuffer="";
    			multiCastSelectChange();
    			break;
    		case RIGHT_ARROW:
    			select=Math.min(select+1,2);
    			dataBuffer="";
    			multiCastSelectChange();
    			break;
    		case CLEAR:
    			if(select < 2)
    				hourData[select].setText("00");
    			if(select == 0)
    				hourData[select].setText("01");
    			dataBuffer="";
    			break;
    		case READY:
    			multiCastReady(); //// multi cast
    			break;
    		}
    	}
    }
    
    private void stepWrite(int numData,boolean up) {
    	numData = up? numData+1:numData-1;
    	switch(select) {
    	case 0:
    		if(numData == 13)
        		numData = 1;
        	else if(numData == 0)
        		numData = 12;
    		break;
    	case 1:
    		if(numData == 60)
        		numData = 0;
        	else if(numData == -1)
        		numData = 59;
    		break;
    	case 2:
    		String oppositeSection = hourData[2].getText().equals("a.m.")? "p.m.":"a.m.";
    		hourData[2].setText(oppositeSection);
    		break;
    	}
    	if(select < 2) {
	    	dataBuffer = String.valueOf(numData);
	    	forceTwoDigit(numData);
	    	dataBuffer="";
	    	multiCastSelectChange();
    	}
    }
    
    private void writeData() {
    	int numData = Integer.parseInt(dataBuffer);
    	switch(select) {
    	case 0:
    		if(dataBuffer.length() == 1) {
    			//On first digit, if it add a second, the number will be higher than 12
    			if(numData > 1) {
    				forceTwoDigit(numData);
    	        	select++;
    	        	dataBuffer="";
    	        	multiCastSelectChange();
    			}
    			//First digit as 1 is available
    			else {
    				forceTwoDigit(numData);
    			}
    		}
			else {
				if(numData <= 12) {
					forceTwoDigit(numData); 
    	        	select++;
    	        	dataBuffer="";
    	        	multiCastSelectChange();
				}
				else {
					numData = 1;
					forceTwoDigit(numData); 
    	        	select++;
    	        	dataBuffer="";
    	        	multiCastSelectChange();
				}
			}
    		break;
    	case 1:
    		if(dataBuffer.length() == 1) {
    			//On first digit, if it add a second, the number will be higher than 59
    			if(numData > 5) {
    				forceTwoDigit(numData);
    	        	select++;
    	        	dataBuffer="";
    	        	multiCastSelectChange();
    			}
    			//First digit as 1 is available
    			else {
    				forceTwoDigit(numData);
    			}
    		}
			else {
				forceTwoDigit(numData); 
	        	select++;
	        	dataBuffer="";
	        	multiCastSelectChange();
			}
    		break;
    	case 2:
    		break;	
    	}
    }
    
    private void multiCastSelectChange() {
    	if(parent instanceof SelectHourPanel)
    		((SelectHourPanel) parent).selectChange();
    	else
    		((SelectDayHourPanel) parent).selectChange();
    }
    
    private void multiCastReady() { ///////??????
    	if(parent instanceof SelectHourPanel)
    		((SelectHourPanel) parent).ready();
    	else
    		((SelectDayHourPanel) parent).ready();
    }
    
    private void forceTwoDigit(int numData) {
    	dataBuffer = String.valueOf(numData);
    	dataBuffer = dataBuffer.length() < 2? "0"+dataBuffer:dataBuffer;
    	hourData[select].setText(dataBuffer);
    }
    private boolean isNumeric(char charNum) {
    	String strNum = String.valueOf(charNum);
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
