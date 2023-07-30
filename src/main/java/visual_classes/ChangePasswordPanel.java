package visual_classes;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.border.LineBorder;

import useful_classes.Encryption;
import useful_classes.FileHandler;
import useful_classes.Movement;
import useful_classes.osChange;
import visual_classes.VisualElements.CustomButton;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class ChangePasswordPanel extends JPanel {
	JPanel componentsPane = new JPanel();
	JLabel current_password_lbl;
	JLabel password_lbl;
	JLabel password_confirmation_lbl;
	JPasswordField current_password_fld;
	JPasswordField password_fld;
	JPasswordField confirmation_fld;
	JLabel show_password_img;
	JButton inicio_btn;
	
	int componentsX;
	int currPasslblY;
	int currPassfldY;
	int passlblY;
	int passfldY;
	int conflblY;
	int conffldY;
	int buttonsY;
	int passFieldLimit;
	int inicioBtnX;
	
	int backX;
	int backY;
	CustomButton back_btn = new VisualElements().new CustomButton("back");
	boolean show_pass = false;
	ImageIcon conOn = new ImageIcon(FirstTimePanel.class.getResource("/ojo_contrasena_on.png"));
	ImageIcon conOff = new ImageIcon(FirstTimePanel.class.getResource("/ojo_contrasena_off.png"));
	ImageIcon inicioIconUnpressed = new ImageIcon(this.getClass().getResource("/inicio_big_btn_unpressed.png"));
	ImageIcon inicioIconPressed = new ImageIcon(this.getClass().getResource("/inicio_big_btn_pressed.png"));
	osChange os = new osChange();
	private boolean componentsUp = false;
	private boolean moveOnce = false;
	Dialog dialog = new Dialog();
	MainPane main;

	/**
	 * Create the panel.
	 */
	public ChangePasswordPanel() {
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
		
		int componentsWidth = 300;
		int componentsHeight = 40;
		int gap = 10;
				
		//Setting this panel
		setOpaque(false);
		setBounds(panelX, panelY, panelWidth, panelHeight);
		//setBounds(0,0,400,250);
		setLayout(null);
		
		//ComponentPane
		componentsPane.setSize(getSize());
		componentsPane.setLocation(0,0);
		componentsPane.setLayout(null);
		componentsPane.setOpaque(false);
		
		//Position and size parameters
		componentsX = (panelWidth/2)-(componentsWidth/2);
		currPasslblY = (getHeight()/2)-(componentsHeight*7+gap*2)/2;
		currPassfldY = currPasslblY + componentsHeight;
		passlblY = currPassfldY + componentsHeight + gap;
		passfldY = passlblY + componentsHeight;
		conflblY = passfldY + componentsHeight + gap;
		conffldY = conflblY + componentsHeight;
		buttonsY = conffldY + componentsHeight+gap;
		passFieldLimit = 16;
		
		//Password label
		current_password_lbl = new JLabel("Contrase\u00F1a actual");
		current_password_lbl.setForeground(Color.WHITE);
		current_password_lbl.setFont(new Font("Alegreya Sans SC", Font.BOLD, 35));
		current_password_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		current_password_lbl.setBounds(componentsX, currPasslblY, componentsWidth, componentsHeight);
		componentsPane.add(current_password_lbl);
		
		//Password label
		password_lbl = new JLabel("Nueva contrase\u00F1a");
		password_lbl.setForeground(Color.WHITE);
		password_lbl.setFont(new Font("Alegreya Sans SC", Font.BOLD, 35));
		password_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		//password_lbl.setBounds(28, 39, 320, 27);
		password_lbl.setBounds(componentsX, passlblY, componentsWidth, componentsHeight);
		componentsPane.add(password_lbl);
		
		//Password confirmation label
		password_confirmation_lbl = new JLabel("Confirmaci\u00F3n");
		password_confirmation_lbl.setForeground(Color.WHITE);
		password_confirmation_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		password_confirmation_lbl.setFont(new Font("Alegreya Sans SC", Font.BOLD, 35));
		//password_confirmation_lbl.setBounds(29, 99, 254, 27);
		password_confirmation_lbl.setBounds(componentsX, conflblY, componentsWidth, componentsHeight);
		componentsPane.add(password_confirmation_lbl);
		
		//Password field
		current_password_fld = new JPasswordField(passFieldLimit);
		current_password_fld.setForeground(Color.WHITE);
		current_password_fld.setOpaque(false);
		current_password_fld.setFont(new Font("Tahoma", Font.PLAIN, 40));
		current_password_fld.setBorder(new LineBorder(Color.WHITE, 2, true));
		//password_fld.setBounds(28, 74, 155, 20);
		current_password_fld.setBounds(componentsX, currPassfldY, componentsWidth, componentsHeight);
		componentsPane.add(current_password_fld);
		
		//Password field
		password_fld = new JPasswordField(passFieldLimit);
		password_fld.setForeground(Color.WHITE);
		password_fld.setOpaque(false);
		password_fld.setFont(new Font("Tahoma", Font.PLAIN, 40));
		password_fld.setBorder(new LineBorder(Color.WHITE, 2, true));
		//password_fld.setBounds(28, 74, 155, 20);
		password_fld.setBounds(componentsX, passfldY, componentsWidth, componentsHeight);
		componentsPane.add(password_fld);
		
		//Confirmation field
		confirmation_fld = new JPasswordField(passFieldLimit);
		confirmation_fld.setForeground(Color.WHITE);
		confirmation_fld.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		confirmation_fld.setOpaque(false);
		confirmation_fld.setFont(new Font("Tahoma", Font.PLAIN, 40));
		//confirmation_fld.setBounds(27, 124, 156, 25);
		confirmation_fld.setBounds(componentsX, conffldY, componentsWidth, componentsHeight);
		componentsPane.add(confirmation_fld);
		
		//Image icon and button parameters
		int scale = 15;
		int conOffWidth = (int)(conOff.getIconWidth()/scale);
		int conOffHeight = (int)(conOff.getIconHeight()/scale);
		int conOnWidth = (int)(conOn.getIconWidth()/scale);
		int conOnHeight = (int)(conOn.getIconHeight()/scale);
		
		conOn = new ImageIcon(conOn.getImage().getScaledInstance(conOnWidth, conOnHeight,java.awt.Image.SCALE_SMOOTH));
		conOff = new ImageIcon(conOff.getImage().getScaledInstance(conOffWidth, conOffHeight,java.awt.Image.SCALE_SMOOTH));
		
		//Show password button
		show_password_img = new JLabel();
		show_password_img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_pass = !show_pass;
				showPassword(show_pass,conOn,conOff);
			}
		});
		show_password_img.setOpaque(false);
		show_password_img.setIcon(conOff);
		show_password_img.setBounds(componentsX, buttonsY, conOffWidth, conOffHeight);
		componentsPane.add(show_password_img);
		
		//Start button
		inicio_btn = new JButton();
		inicio_btn.setFont(new Font("Alegreya Sans SC", Font.PLAIN, 25));
		int inicioBtnWidth = 150;
		inicioBtnX = componentsX + componentsWidth/2 - inicioBtnWidth/2;
		inicio_btn.setBounds(inicioBtnX, buttonsY, inicioBtnWidth, componentsHeight);
		inicioIconUnpressed = new ImageIcon(inicioIconUnpressed.getImage().getScaledInstance(inicioBtnWidth, componentsHeight,java.awt.Image.SCALE_SMOOTH));
		inicioIconPressed = new ImageIcon(inicioIconPressed.getImage().getScaledInstance(inicioBtnWidth, componentsHeight,java.awt.Image.SCALE_SMOOTH));
		inicio_btn.setIcon(inicioIconUnpressed);
		inicio_btn.setPressedIcon(inicioIconPressed);
		inicio_btn.setBorder(null);
		inicio_btn.setContentAreaFilled(false);
		componentsPane.add(inicio_btn);
		componentsPane.add(back_btn);
		add(dialog);
		add(componentsPane);
		
		
	}
	public void setMainPane(MainPane main) {
		this.main = main;
		backX = this.main.back_btn.getLocation().x;
		backY = this.main.back_btn.getLocation().y-280;
		moveOnce = false;
		setBackButton();
		setActions();
	}
	
	public void moveComponents(boolean leaving) {
		componentsUp = !componentsUp;
		int move = (componentsUp)? -150:0;
		//int backMove = (componentsUp)? -350:0;
		
		Movement keyboardMove = new Movement(main.virtualKeyboard);
		Movement componentMove = new Movement(this.componentsPane);
		//componentsPane.setLocation(0,move);
		
		
		int pos[] = main.virtualKeyboard.getHeights();
		main.virtualKeyboard.moveToInitialHeight();
		keyboardMove.setSlowedDownMovement(pos[1],100,0.10f,'y');
		keyboardMove.start();
		
		if(componentsUp) {
			componentMove.setSlowedDownMovement(move,100,0.50f,'y');
			componentMove.start();
		}else
			componentsPane.setLocation(componentsPane.getLocation().x,move);
		
		
		main.virtualKeyboard.setVisible(componentsUp);
		main.back_btn.setVisible(!componentsUp && !leaving);
		back_btn.setVisible(componentsUp);
		//back_btn.setLocation(backX,backY+backMove);

	}
	
	private void setBackButton(){
		ImageIcon backU = new ImageIcon(this.getClass().getResource("/back_btn_unpressed.png"));
		ImageIcon backP = new ImageIcon(this.getClass().getResource("/back_btn_pressed.png"));
		backU = new ImageIcon(backU.getImage().getScaledInstance( main.squareButtonSize, main.squareButtonSize,java.awt.Image.SCALE_SMOOTH));
		backP = new ImageIcon(backP.getImage().getScaledInstance( main.squareButtonSize, main.squareButtonSize,java.awt.Image.SCALE_SMOOTH));
		back_btn.setBounds(backX,backY,main.squareButtonSize,main.squareButtonSize);
		back_btn.setIcon(backU);
		back_btn.setPressedIcon(backP);
		back_btn.setBorder(null);
		back_btn.setContentAreaFilled(false);
		back_btn.setVisible(false);
	}
	
	public void setActions() {
		back_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				moveComponents(true);
				main.dateAndHour.update();
				main.menuNavegation.goBack(main.atribute);
				//main.virtualKeyboard.setVisible(false);
				moveOnce = false;
				
				show_pass = false;
				showPassword(show_pass,conOn,conOff);
				current_password_fld.setText("");
				password_fld.setText("");
				confirmation_fld.setText("");
				main.virtualKeyboard.setHeight(
						main.virtualKeyboard.defaultHeight);
				dialog.setVisible(false);
			}
		});
		current_password_fld.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!moveOnce) {
					main.lock_btn.setVisible(false);
					moveOnce = true;
					moveComponents(false);
					
				}
			}
		});	
		password_fld.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!moveOnce) {
					main.lock_btn.setVisible(false);
					moveOnce = true;
					moveComponents(false);
					
				}
			}
		});	
		confirmation_fld.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(!moveOnce) {
					main.virtualKeyboard.setVisible(true);
					main.lock_btn.setVisible(false);
					main.back_btn.setVisible(false);
					moveOnce = true;
					moveComponents(false);
				}
			}
		});	
		
		inicio_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean confirmed;
				boolean correct;
				main.dateAndHour.update();
				
				//Habilitando la encriptacin
				FileHandler passRead = main.passRead;
				//Encryption hash = new Encryption();
				String enc_pass = Encryption.sha1(current_password_fld.getPassword());
				String[] passLine = passRead.readFileLine(1).trim().split("\\s+");
				
				correct = main.startSessionPane.passwordCorrect(
						main.startSessionPane.stringToChar(passLine[1]),
						main.startSessionPane.stringToChar(enc_pass));
				
				confirmed = passwordConfirmed(password_fld.getPassword(),confirmation_fld.getPassword());
				if(confirmed && !(password_fld.getPassword().length == 0) && correct) {
					//main.atribute.setup = false;
					main.principalPane.reset(true);
					main.menuNavegation.goToMain(main.atribute);
					main.virtualKeyboard.setHeight(main.virtualKeyboard.defaultHeight);
					show_pass = false;
					showPassword(show_pass,conOn,conOff);
					
					//Habilitando la encriptacin
					//Encryption hash = new Encryption();
					enc_pass = Encryption.sha1(password_fld.getPassword());
					
					//Guardando la contrasea
					FileHandler fl = new FileHandler();
					fl.setDirection("sav");
					fl.setFilename("main_data.int");
					fl.writeFile("qzr ",false);
					fl.writeFileln(enc_pass,true);
					
					main.atribute.first = false;
					moveComponents(true);
					moveOnce = false;
					dialog.setVisible(false);
				}
				else if(!correct) {
					dialog.incorrectPasswordMessage();
				}
				else if(!confirmed) {
					dialog.incorrectConfirmationMessage();
				}
				current_password_fld.setText("");
				password_fld.setText("");
				confirmation_fld.setText("");
				
			}
		});
	}
	
	public void showPassword(boolean pass,ImageIcon on,ImageIcon off) {
		if(pass) {
			show_password_img.setIcon(on);
			current_password_fld.setEchoChar((char)0);
			password_fld.setEchoChar((char)0);
			confirmation_fld.setEchoChar((char)0);
		}
		else {
			show_password_img.setIcon(off);
			current_password_fld.setEchoChar('●');
			password_fld.setEchoChar('●');
			confirmation_fld.setEchoChar('●');
		}
	}
	
	public boolean passwordConfirmed(char[] pass,char [] confir) {
		   boolean confirmed = true;
		   if (pass.length != confir.length) {
		       confirmed = false;
		   } else {
			   confirmed = Arrays.equals(pass, confir);
		   }
		   return confirmed;
		}
}