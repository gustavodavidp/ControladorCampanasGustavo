package visual_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import useful_classes.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StartSessionPanel extends JPanel {
	JLabel password_lbl;
	JPasswordField password_fld;
	JLabel show_password_img;
	JButton inicio_btn;
	JPanel componentsPane = new JPanel();
	boolean show_pass = false;
	ImageIcon conOn = new ImageIcon(StartSessionPanel.class.getResource("/ojo_contrasena_on.png"));
	ImageIcon conOff = new ImageIcon(StartSessionPanel.class.getResource("/ojo_contrasena_off.png"));
	ImageIcon inicioIconUnpressed = new ImageIcon(this.getClass().getResource("/inicio_big_btn_unpressed.png"));
	ImageIcon inicioIconPressed = new ImageIcon(this.getClass().getResource("/inicio_big_btn_pressed.png"));
	
	MainPane main;
	FileHandler passRead = new FileHandler();
	osChange os = new osChange();
	
	//Position and size parameters
	int componentsX;
	int passlblY;
	int passfldY;
	int buttonsY;
	int passFieldLimit;
	
	int scale = 15;
	int conOffWidth = (int)(conOff.getIconWidth()/scale);
	int conOffHeight = (int)(conOff.getIconHeight()/scale);
	int conOnWidth = (int)(conOn.getIconWidth()/scale);
	int conOnHeight = (int)(conOn.getIconHeight()/scale);
	
	int inicioBtnWidth;
	int inicioBtnX;
	
	int componentsWidth = 300;
	int componentsHeight = 40;
	int gap = 10;
	
	private boolean componentsUp = false;
	private boolean moveOnce = false;
	Dialog dialog = new Dialog();
	/**
	 * Create the panel.
	 */
	public StartSessionPanel() {
		//Setting size parameters
		//Screen
		Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		
		//Panel
		int panelWidth = screenWidth;
		int panelHeight = screenHeight;
		int panelX=(screenWidth/2)-(panelWidth/2);
		int panelY=0;
			
		//Setting this panel
		setOpaque(false);
		setBounds(panelX, panelY, panelWidth, panelHeight);
		setLayout(null);
		

		//Position and size parameters
		componentsX = (panelWidth/2)-(componentsWidth/2);
		passlblY = (getHeight()/2)-(componentsHeight*3+gap)/2;
		passfldY = passlblY + componentsHeight;
		buttonsY = passfldY + componentsHeight+gap;
		passFieldLimit = 16;
		
		//ComponentPane
		componentsPane.setSize(getSize());
		componentsPane.setLocation(0,0);
		componentsPane.setLayout(null);
		componentsPane.setOpaque(false);
		
		//Password label
		password_lbl = new JLabel("Ingrese la contrase\u00F1a");
		password_lbl.setForeground(Color.WHITE);
		password_lbl.setFont(new Font("Alegreya Sans SC", Font.BOLD, 30));
		password_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		password_lbl.setBounds(componentsX, passlblY, componentsWidth, componentsHeight);
		componentsPane.add(password_lbl);
					
		//Password field
		password_fld = new JPasswordField(passFieldLimit);											
		password_fld.setForeground(Color.WHITE);
		password_fld.setOpaque(false);
		password_fld.setFont(new Font("Tahoma", Font.PLAIN, 40));
		password_fld.setBorder(new LineBorder(Color.WHITE, 2, true));
		password_fld.setBounds(componentsX, passfldY, componentsWidth, componentsHeight);
		componentsPane.add(password_fld);
		
		conOn = new ImageIcon(conOn.getImage().getScaledInstance(conOnWidth, conOnHeight,java.awt.Image.SCALE_SMOOTH));
		conOff = new ImageIcon(conOff.getImage().getScaledInstance(conOffWidth, conOffHeight,java.awt.Image.SCALE_SMOOTH));
		
		//Show password button
		show_password_img = new JLabel();
		show_password_img.setOpaque(false);
		show_password_img.setBorder(null);
		show_password_img.setIcon(conOff);
		show_password_img.setBounds(componentsX, buttonsY, conOffWidth, conOffHeight);
		componentsPane.add(show_password_img);
		
		//Start button
		inicio_btn = new JButton();		
		inicioBtnWidth = 150;
		inicioBtnX = componentsX + componentsWidth/2 - inicioBtnWidth/2;
		inicioIconUnpressed = new ImageIcon(inicioIconUnpressed.getImage().getScaledInstance(inicioBtnWidth, componentsHeight,java.awt.Image.SCALE_SMOOTH));
		inicioIconPressed = new ImageIcon(inicioIconPressed.getImage().getScaledInstance(inicioBtnWidth, componentsHeight,java.awt.Image.SCALE_SMOOTH));
		inicio_btn.setBounds(inicioBtnX, buttonsY, inicioBtnWidth, componentsHeight);
		inicio_btn.setIcon(inicioIconUnpressed);
		inicio_btn.setPressedIcon(inicioIconPressed);
		inicio_btn.setBorder(null);
		inicio_btn.setContentAreaFilled(false);
		componentsPane.add(inicio_btn);
		add(dialog);
		add(componentsPane);
		
	}
	
	public void setMainPane(MainPane main) {
		this.main = main;
		passRead = this.main.passRead;
		setActions();
	}
	
	public void moveComponents() {
		componentsUp = !componentsUp;
		int move = (componentsUp)? -160:0;
		
		if(componentsUp) {
			Movement move1 = new Movement(this.componentsPane);
			move1.setSlowedDownMovement(move,100,0.50f,'y');
			move1.start();
		}else
			componentsPane.setLocation(componentsPane.getLocation().x,move);
		/*password_lbl.setBounds(componentsX, passlblY+move, componentsWidth, componentsHeight);
		password_fld.setBounds(componentsX, passfldY+move, componentsWidth, componentsHeight);
		show_password_img.setBounds(componentsX, buttonsY+move, conOffWidth, conOffHeight);
		inicio_btn.setBounds(inicioBtnX, buttonsY+move, inicioBtnWidth, componentsHeight);*/
	}
	
	private void setActions() {
		password_fld.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!moveOnce) {
					int pos[] = main.virtualKeyboard.getHeights();
					main.virtualKeyboard.moveToInitialHeight();
					//System.out.println("Initial pos: "+main.virtualKeyboard.getLocation().y);
					//System.out.println("Final pos: "+pos[1]);
					main.virtualKeyboard.setVisible(true);
					Movement move = new Movement(main.virtualKeyboard);
					move.setSlowedDownMovement(pos[1],100,0.10f,'y');
					move.start();
					main.screen_btn.setVisible(false);
					main.startSessionPane.moveComponents();
					moveOnce = true;
				}
			}
		});
		
		inicio_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("clicked");
				boolean correct;
				main.dateAndHour.update();
				
				main.principalPane.dialog.setVisible(false);
				main.principalPane.selectedData="";
				//Habilitando la encriptacin
				//Encryption hash = new Encryption();
				String enc_pass = Encryption.sha1(password_fld.getPassword());
				String[] passLine = passRead.readFileLine(1).trim().split("\\s+");
				
				correct = passwordCorrect(stringToChar(passLine[1]),stringToChar(enc_pass));
				if(correct) {
					show_pass = false;
					showPassword(show_pass,conOn,conOff);
					main.principalPane.reset(true);	
					main.menuNavegation.next(main.atribute);
					password_fld.setText("");
					
					main.virtualKeyboard.setVisible(false);
					main.startSessionPane.moveComponents();
					moveOnce = false;
					dialog.setVisible(false);
				}
				else {
					dialog.incorrectPasswordMessage();
					password_fld.setText("");
					password_fld.requestFocus();
				}
			}
		});
		
		password_fld.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				main.dateAndHour.update();
			}
		    });
		
		show_password_img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				show_pass = !show_pass;
				showPassword(show_pass,conOn,conOff);
			}
		});
	}
	
	public void showPassword(boolean pass,ImageIcon on,ImageIcon off) {
		if(pass) {
			show_password_img.setIcon(on);
			password_fld.setEchoChar((char)0);
		}
		else {
			show_password_img.setIcon(off);
			password_fld.setEchoChar('●');
		}
	}
	
	public boolean passwordCorrect(char[] pass,char [] key) {
		   boolean correct = true;
		   if (pass.length != key.length) {
		       correct = false;
		   } else {
			   correct = Arrays.equals(pass, key);
		   }
		   return correct;
		}
	
	public char[] stringToChar(String str) {
		
		// Creating array of string length
        char[] ch = new char[str.length()];
  
        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        return ch;
	}
}
