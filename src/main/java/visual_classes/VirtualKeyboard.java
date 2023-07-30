package visual_classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

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
public class VirtualKeyboard  extends JPanel implements FocusListener{
	private int rowY = 0;
	private int alpha = 255;
	public int defaultHeight = 350;
	osChange os = new osChange();
	int finalHeight;
	int initialHeight;
    /**
     * Private class for storing key specification.
     */
    private class Key {

        public final int keyCode;
        public final String value;
        public final String shiftValue;

        public Key(int keyCode, String value, String shiftValue) {
            this.keyCode = keyCode;
            this.value = value;
            this.shiftValue = shiftValue;
        }

        public Key(int keyCode, String value) {
            this(keyCode, value, value);
        }

        public boolean hasShiftValue() {
            return !this.value.equals(this.shiftValue);
        }

        public boolean isLetter() {
            return value.length() == 1
                    && Character.isLetter(value.toCharArray()[0]);
        }
    }

 // Special keys definition
    //private final Key TAB_KEY = new Key(KeyEvent.VK_TAB, "Tab");
    private final Key PARENTHESIS_KEY = new Key(KeyEvent.VK_LEFT_PARENTHESIS,"(",")");
    private final Key CAPS_LOCK_KEY = new Key(KeyEvent.VK_CAPS_LOCK, "Caps");
    private final Key SHIFT_KEY = new Key(KeyEvent.VK_SHIFT, String.valueOf('\u2191'));
    private final Key ACUTE_KEY = new Key(KeyEvent.VK_DEAD_ACUTE, "´", "`");
    private final Key GRAVE_KEY = new Key(KeyEvent.VK_DEAD_GRAVE, "`");
    private final Key TILDE_CIRCUMFLEX_KEY = new Key(KeyEvent.VK_DEAD_TILDE, "~", "^");
    private final Key CIRCUMFLEX_KEY = new Key(KeyEvent.VK_DEAD_TILDE, "^");

    // First key row
    private Key[] row1 = new Key[]{
        new Key(KeyEvent.VK_QUOTE, "'", "\""),
        new Key(KeyEvent.VK_1, "1"), new Key(KeyEvent.VK_2, "2"),
        new Key(KeyEvent.VK_3, "3"), new Key(KeyEvent.VK_4, "4"),
        new Key(KeyEvent.VK_5, "5"), new Key(KeyEvent.VK_6, "6"),
        new Key(KeyEvent.VK_7, "7"), new Key(KeyEvent.VK_8, "8"),
        new Key(KeyEvent.VK_9, "9"), new Key(KeyEvent.VK_0, "0"),
        new Key(KeyEvent.VK_MINUS, "-", "_"),
        new Key(KeyEvent.VK_BACK_SPACE, String.valueOf('\u2190'))
    };

    // Second key row
    private Key[] row2 = new Key[]{
        PARENTHESIS_KEY,
        new Key(KeyEvent.VK_Q, "q","Q"), new Key(KeyEvent.VK_W, "w","W"),
        new Key(KeyEvent.VK_E, "e","E"), new Key(KeyEvent.VK_R, "r","R"),
        new Key(KeyEvent.VK_T, "t","T"), new Key(KeyEvent.VK_Y, "y","Y"),
        new Key(KeyEvent.VK_U, "u","U"), new Key(KeyEvent.VK_I, "i","I"),
        new Key(KeyEvent.VK_O, "o","O"), new Key(KeyEvent.VK_P, "p","P"),
        ACUTE_KEY,
        new Key(KeyEvent.VK_BRACELEFT, "[", "{")
    };

    // Third key row
    private Key[] row3 = new Key[]{
        CAPS_LOCK_KEY,
        new Key(KeyEvent.VK_A, "a","A"), new Key(KeyEvent.VK_S, "s","S"),
        new Key(KeyEvent.VK_D, "d","D"), new Key(KeyEvent.VK_F, "f","F"),
        new Key(KeyEvent.VK_G, "g","G"), new Key(KeyEvent.VK_H, "h","H"),
        new Key(KeyEvent.VK_J, "j","J"), new Key(KeyEvent.VK_K, "k","K"),
        new Key(KeyEvent.VK_L, "l","L"), new Key(KeyEvent.VK_DEAD_CEDILLA, "ç","Ç"),
        TILDE_CIRCUMFLEX_KEY,
        new Key(KeyEvent.VK_BRACERIGHT, "]", "}")
    };

    // Fourth key row
    private Key[] row4 = new Key[]{
        SHIFT_KEY,
        new Key(KeyEvent.VK_BACK_SLASH, "\\", "|"),
        new Key(KeyEvent.VK_Z, "z","Z"), new Key(KeyEvent.VK_X, "x","X"),
        new Key(KeyEvent.VK_C, "c","C"), new Key(KeyEvent.VK_V, "v","V"),
        new Key(KeyEvent.VK_B, "b","B"), new Key(KeyEvent.VK_N, "n","N"),
        new Key(KeyEvent.VK_M, "m","M"), new Key(KeyEvent.VK_COMMA, ",", "<"),
        new Key(KeyEvent.VK_PERIOD, ".", ">"),
        new Key(KeyEvent.VK_SEMICOLON, ";", ":"),
        new Key(KeyEvent.VK_SLASH, "/", "?")
    };

    private final Map<Key, JButton> buttons;
    private Component currentComponent;
    private JTextComponent lastFocusedTextComponent;
    private JFrame frame;
    private boolean isCapsLockPressed = false;
    private boolean isShiftPressed = false;
    private Color defaultColor;
    private Key accentuationBuffer;

    public VirtualKeyboard() {
        this.buttons = new HashMap<>();
    }

    /**
     * Initializes the virtual keyboard and shows in the informed JFrame.
     *
     * @param frame JFrame that will be used to show the virtual keyboard.
     * @param keyboardPanel The panel where this keyboard will be held.
     * 
     */
    public void setFrame(JFrame frame) {
    	setPanel();
        this.frame = frame;
        currentComponent = frame.getFocusOwner();
        if (currentComponent == null) {
            currentComponent = frame.getFocusTraversalPolicy().getFirstComponent(frame);
        } 
        Color keyColor = getBackground();
        add(initRow(row1, getSize(),keyColor));
        add(initRow(row2, getSize(),keyColor));
        add(initRow(row3, getSize(),keyColor));
        add(initRow(row4, getSize(),keyColor));
    }
    
    public void setHeight(int height) {
    	rowY = 0;
    	Component[] rows = getComponents();
    	for(Component row: rows) {
    		setRowHeight((JPanel)row,height);
    	}
    	Dimension screenSize = os.setDimension();
    	int screenHeight = (int)screenSize.getHeight();
    	finalHeight = screenHeight - height;
    	setPanel(height);	
    }
        
    public int[] getHeights( ){  
	    int[] pos = new int [2];    // Creating an array of 3 elements  
	    pos[0]=initialHeight;  
	    pos [1]=finalHeight;  
	    return pos;            // Return the reference of the array  
    }
    
    public void moveToInitialHeight() {
    	setLocation(getLocation().x,initialHeight);
    }
    
    public void setPanel() {
    	Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
    	
//		keyPan.setBackground(Color.BLACK);
		setBackground(new Color(0,0,0,200));
		
		//pan.setOpaque(false);
		int keyPanWidth = screenWidth;
		int keyPanHeight = defaultHeight;
		int keyPanX = screenWidth/2 - keyPanWidth/2;
		int keyPanY = screenHeight - keyPanHeight;
		finalHeight = keyPanY;
		initialHeight = screenHeight;
		setBounds(keyPanX,keyPanY,keyPanWidth,keyPanHeight);
		setLayout(null);
    }
    
    public void setPanel(int height) {
    	Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
    	
//		keyPan.setBackground(Color.BLACK);
		setBackground(new Color(0,0,0,200));
		
		//pan.setOpaque(false);
		int keyPanWidth = screenWidth;
		int keyPanHeight = height; //350
		int keyPanX = screenWidth/2 - keyPanWidth/2;
		//int keyPanY = screenHeight/2 - keyPanHeight/2+100;
		int keyPanY = screenHeight - keyPanHeight;
		finalHeight = keyPanY;
		initialHeight = screenHeight;
		setBounds(keyPanX,keyPanY,keyPanWidth,keyPanHeight);
		//add(keyPan);
		//key.show(frame,keyPan);
		//screen_btn.setVisible(false);
		setLayout(null);
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
    
    private void setRowHeight(JPanel row, int height) {
    	int outerButtonsGap = 10;
    	int innerButtonsGap = 10;
        int buttonHeight = (height - outerButtonsGap*2 - innerButtonsGap*3) / 4; // number of rows
        
        Component[] btn = (Component[]) row.getComponents();
        for (int i = 0; i < btn.length; ++i) {
            Dimension btnSize = btn[i].getSize();
            btnSize.height = buttonHeight;
            btn[i].setSize(btnSize);
        }
        
        int pY = outerButtonsGap+rowY;
        int pHeight = buttonHeight;
        Dimension rowSize = row.getSize();
        Point rowLocation = row.getLocation();
        rowLocation.y = pY;
        rowSize.height = pHeight;
        row.setBounds(rowLocation.x,rowLocation.y,rowSize.width,rowSize.height);
        rowY += pHeight + innerButtonsGap;
    }
        

    
    private JPanel initRow(Key[] keys, Dimension dimensions, Color panelColor) {
    	int outerButtonsGap = 10;
    	int innerButtonsGap = 10;
    	
        JPanel p = new JPanel();
        p.setLayout(null);
        int buttonWidth = (dimensions.width - outerButtonsGap*2 - innerButtonsGap*(keys.length-1) ) / keys.length;
        int buttonHeight = (dimensions.height - outerButtonsGap*2 - innerButtonsGap*3) / 4; // number of rows
                
        int compensationButtonX = Math.round(dimensions.width-outerButtonsGap*2)/2-(buttonWidth*keys.length+innerButtonsGap*(keys.length-1))/2;
        for (int i = 0; i < keys.length; ++i) {
            Key key = keys[i];
            JButton button;
            if (buttons.containsKey(key)) {
                button = buttons.get(key);
            } else {
                button = new JButton(key.value);
                int lineThickness = 2;
                
                /*Color btnLineColor = getColorWithAlpha(Color.WHITE);
                Color btnColor = getColorWithAlpha(Color.BLACK);
                Color btnTextColor = getColorWithAlpha(Color.WHITE);*/
               
                button.setBorder(new MatteBorder(lineThickness, lineThickness, lineThickness, lineThickness, (Color) Color.WHITE));
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(0,0,0,255));
                //button.setContentAreaFilled(true);
                //button.setOpaque(false);
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
                
                button.setFont(new Font("Tahoma", Font.PLAIN, 20));
                int buttonX = compensationButtonX+i*(buttonWidth+innerButtonsGap);
                button.setBounds(buttonX,0,buttonWidth, buttonHeight);
                button.addFocusListener(this);
                buttons.put(key, button);
                button.addActionListener(e -> actionListener(key));
            }
  
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

    private void actionListener(Key key) {
        if (currentComponent == null || !(currentComponent instanceof JComponent)) {
            return;
        }
        ((JComponent) currentComponent).requestFocus();
        JTextComponent currentTextComponent = getCurrentTextComponent();
        switch (key.keyCode) {
            case KeyEvent.VK_CAPS_LOCK:
                capsLockPressed();
                break;
            case KeyEvent.VK_SHIFT:
                shiftPressed();
                break;
            case KeyEvent.VK_BACK_SPACE:
                if (currentTextComponent == null) {
                    return;
                }
                backspacePressed(currentTextComponent);
                break;
            default:
                if (currentTextComponent == null) {
                    return;
                }
                otherKeyPressed(key, currentTextComponent);
                break;
        }
    }

    private void capsLockPressed() {
        isCapsLockPressed = !isCapsLockPressed;
        buttons.forEach((k, b) -> {
            if (k.isLetter() && k.hasShiftValue()) {
                if (isCapsLockPressed) {
                    b.setText(k.shiftValue);
                } else {
                    b.setText(k.value);
                }
            }
        });
        if (isCapsLockPressed) {
            if (defaultColor == null) {
                defaultColor = buttons.get(SHIFT_KEY).getBackground();
            }
            buttons.get(CAPS_LOCK_KEY).setBackground(Color.orange);
        } else {
            buttons.get(CAPS_LOCK_KEY).setBackground(defaultColor);
        }
    }

    private void shiftPressed() {
        isShiftPressed = !isShiftPressed;
        buttons.forEach((k, b) -> {
            if (k.hasShiftValue()) {
                if (isShiftPressed) {
                    b.setText(k.shiftValue);
                } else {
                    b.setText(k.value);
                }
            }
        });
        if (isShiftPressed) {
            if (defaultColor == null) {
                defaultColor = buttons.get(SHIFT_KEY).getBackground();
            }
            buttons.get(SHIFT_KEY).setBackground(Color.orange);
        } else {
            buttons.get(SHIFT_KEY).setBackground(defaultColor);
        }
    }

    private void backspacePressed(JTextComponent component) {
        if (currentComponent instanceof JTextComponent) {
            int caretPosition = component.getCaretPosition();
            if (!component.getText().isEmpty() && caretPosition > 0) {
                try {
                    component.setText(component.getText(0, caretPosition - 1)
                            + component.getText(caretPosition,
                                    component.getText().length() - caretPosition));
                } catch (BadLocationException ex) {
                }
                component.setCaretPosition(caretPosition - 1);
            }
        }
    }

    private void otherKeyPressed(Key key, JTextComponent currentTextComponent) {
        if (key.isLetter()) {
            String keyString;
            if (accentuationBuffer == null) {
                keyString = key.value;
            } else {
                switch (key.keyCode) {
                    case KeyEvent.VK_A:
                        keyString = accentuationBuffer
                                == ACUTE_KEY ? "Ã¡"
                                        : accentuationBuffer == GRAVE_KEY ? "Ã "
                                                : accentuationBuffer == TILDE_CIRCUMFLEX_KEY ? "Ã£"
                                                        : accentuationBuffer == CIRCUMFLEX_KEY ? "Ã¢" : key.value;
                        break;
                    case KeyEvent.VK_E:
                        keyString = accentuationBuffer
                                == ACUTE_KEY ? "Ã©"
                                        : accentuationBuffer == GRAVE_KEY ? "Ã¨"
                                                : accentuationBuffer == TILDE_CIRCUMFLEX_KEY ? "~e"
                                                        : accentuationBuffer == CIRCUMFLEX_KEY ? "Ãª" : key.value;
                        break;
                    case KeyEvent.VK_I:
                        keyString = accentuationBuffer
                                == ACUTE_KEY ? "Ã­"
                                        : accentuationBuffer == GRAVE_KEY ? "Ã¬"
                                                : accentuationBuffer == TILDE_CIRCUMFLEX_KEY ? "~i"
                                                        : accentuationBuffer == CIRCUMFLEX_KEY ? "Ã®" : key.value;
                        break;
                    case KeyEvent.VK_O:
                        keyString = accentuationBuffer
                                == ACUTE_KEY ? "Ã³"
                                        : accentuationBuffer == GRAVE_KEY ? "Ã²"
                                                : accentuationBuffer == TILDE_CIRCUMFLEX_KEY ? "Ãµ"
                                                        : accentuationBuffer == CIRCUMFLEX_KEY ? "Ã´" : key.value;
                        break;
                    case KeyEvent.VK_U:
                        keyString = accentuationBuffer
                                == ACUTE_KEY ? "Ãº"
                                        : accentuationBuffer == GRAVE_KEY ? "Ã¹"
                                                : accentuationBuffer == TILDE_CIRCUMFLEX_KEY ? "~u"
                                                        : accentuationBuffer == CIRCUMFLEX_KEY ? "Ã»" : key.value;
                    default:
                        keyString = key.value;
                        break;
                }
                accentuationBuffer = null;
            }
            if (isCapsLockPressed) {
                keyString = keyString.toUpperCase();
                if (isShiftPressed) {
                    shiftPressed();
                }
            } else if (isShiftPressed) {
                keyString = keyString.toUpperCase();
                shiftPressed();
            }
            addText(currentTextComponent, keyString);
        } else if (key == ACUTE_KEY || key == TILDE_CIRCUMFLEX_KEY) {
            if (key == ACUTE_KEY) {
                if (!isShiftPressed) {
                    accentuationBuffer = key;
                } else {
                    accentuationBuffer = GRAVE_KEY;
                }
            } else if (key == TILDE_CIRCUMFLEX_KEY) {
                if (!isShiftPressed) {
                    accentuationBuffer = key;
                } else {
                    accentuationBuffer = CIRCUMFLEX_KEY;
                }
            }
            if (isShiftPressed) {
                shiftPressed();
            }
        } else {
            String keyString;
            if (isCapsLockPressed) {
                keyString = key.value.toUpperCase();
                if (isShiftPressed) {
                    shiftPressed();
                }
            } else if (isShiftPressed) {
                keyString = key.shiftValue;
                shiftPressed();
            } else {
                keyString = key.value;
            }
            addText(currentTextComponent, keyString);
        }
    }

    private JTextComponent getCurrentTextComponent() {
        if (currentComponent != null && currentComponent instanceof JTextComponent) {
            return (JTextComponent) currentComponent;
        } else {
            return null;
        }
    }

    /**
     * Adds text considering the caret position.
     *
     * @param component Text component.
     * @param text Text that will be added.
     */
    private void addText(JTextComponent component, String text) {
        int caretPosition = component.getCaretPosition();
        try {
            component.setText(component.getText(0, caretPosition)
                    + text + component.getText(caretPosition,
                            component.getText().length() - caretPosition));
            component.setCaretPosition(caretPosition + 1);
        } catch (BadLocationException ex) {
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    	//System.out.println(e.getOppositeComponent());
        Component previousComponent = e.getOppositeComponent();
        if (previousComponent != null && !(previousComponent instanceof JButton
                && buttons.values().contains((JButton) previousComponent))) {
            this.currentComponent = previousComponent;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
    /*	System.out.println("lost");
    	System.out.println(e.getOppositeComponent());
    	Component focusComponent = e.getOppositeComponent();
        if (focusComponent != null && !(focusComponent instanceof JTextComponent)) {
            this.keyPanel.setVisible(false);
            System.out.println("if lost");
        }*/
    }
    
}
