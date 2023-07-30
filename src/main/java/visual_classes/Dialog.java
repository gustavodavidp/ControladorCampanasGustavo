package visual_classes;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class Dialog extends JPanel {
	float scale = 1.5f;
	int shadow = 5;
	int windowWidth = 480 + shadow;
	int bigWindowHeight = 286 + shadow;
	int smallWindowHeight = 205 + shadow;
	
	int bigWindowBtnWidth = 150;
	int bigWindowBtnHeight = 62;
	int okBtnWidth = 155;
	int okBtnHeight = 62;
	
	int siBtnX = 30 - shadow;
	int siBtnY = 194 - shadow;
	int noBtnX = 300 - shadow;
	int noBtnY = 194 - shadow;
	int okBtnX = Math.round((float)(163 - shadow)/scale);
	int okBtnY = Math.round((float)(112 - shadow)/scale)+40;
	
	JButton siBtn = new JButton();
	JButton noBtn = new JButton();
	JButton okBtn = new JButton();
	JLabel seguroQueDeseaLabel = new JLabel("¿Seguro que desea");
	JLabel eliminarLaEjecucionLabel = new JLabel("eliminar la ejecución?");
	JLabel contrasenaIncorrectaLabel = new JLabel("Contraseña incorrecta");
	JLabel confirmacionIncorrectaLabel = new JLabel("Confirmación incorrecta");
	JLabel bigWindowBackground = new JLabel();
	JLabel smallWindowBackground = new JLabel();
	
	
	ImageIcon messageBoxBigIco;// = new ImageIcon(this.getClass().getResource("/message_box_big.png"));
	ImageIcon messageBoxSmallIco;// = new ImageIcon(this.getClass().getResource("/message_box_small.png"));
	ImageIcon siBtnUnpressedIco;// = new ImageIcon(this.getClass().getResource("/si_btn_unpressed.png"));
	ImageIcon noBtnUnpressedIco;// = new ImageIcon(this.getClass().getResource("/no_btn_unpressed.png"));
	ImageIcon okBtnUnpressedIco;// = new ImageIcon(this.getClass().getResource("/ok_btn_unpressed.png"));
	ImageIcon siBtnPressedIco;// = new ImageIcon(this.getClass().getResource("/si_btn_pressed.png"));
	ImageIcon noBtnPressedIco;// = new ImageIcon(this.getClass().getResource("/no_btn_pressed.png"));
	ImageIcon okBtnPressedIco;// = new ImageIcon(this.getClass().getResource("/ok_btn_pressed.png"));
	
	int smallBackgroundWidth;// = Math.round((float)(messageBoxSmallIco.getIconWidth())/scale);
	int smallBackgroundHeight;// = Math.round((float)(messageBoxSmallIco.getIconHeight())/scale);
	int smallBtnWidth;// = Math.round((float)(okBtnUnpressedIco.getIconWidth())*scale);
	int smallBtnHeight;// = Math.round((float)(okBtnUnpressedIco.getIconHeight())*scale);
	Font font = new Font("Alegreya Sans SC", Font.BOLD, 40);
	Font smallFont = new Font("Alegreya Sans SC", Font.BOLD, Math.round(40/scale));
	

	public Dialog() {
	//System.out.println(getClass().getResource("/message_box_big.png"));
	messageBoxBigIco = new ImageIcon(this.getClass().getResource("/message_box_big.png"));
	messageBoxSmallIco = new ImageIcon(this.getClass().getResource("/message_box_small.png"));
	siBtnUnpressedIco = new ImageIcon(this.getClass().getResource("/si_btn_unpressed.png"));
	noBtnUnpressedIco = new ImageIcon(this.getClass().getResource("/no_btn_unpressed.png"));
	okBtnUnpressedIco = new ImageIcon(this.getClass().getResource("/ok_btn_unpressed.png"));
	siBtnPressedIco = new ImageIcon(this.getClass().getResource("/si_btn_pressed.png"));
	noBtnPressedIco = new ImageIcon(this.getClass().getResource("/no_btn_pressed.png"));
	okBtnPressedIco = new ImageIcon(this.getClass().getResource("/ok_btn_pressed.png"));
	smallBackgroundWidth = Math.round((float)(messageBoxSmallIco.getIconWidth())/scale);
	smallBackgroundHeight = Math.round((float)(messageBoxSmallIco.getIconHeight())/scale);
	smallBtnWidth = Math.round((float)(okBtnUnpressedIco.getIconWidth())*scale);
	smallBtnHeight = Math.round((float)(okBtnUnpressedIco.getIconHeight())*scale);
		setVisible(false);
		setBorder(null);
		setOpaque(false);
		setLayout(null);
		setComponents();
		
		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				setVisible(false);
			}
		});
		
		noBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				setVisible(false);
			}
		});
	}
	
	public void setComponents() {
		//Big dialog
		int width = siBtnUnpressedIco.getIconWidth();
		int height = siBtnUnpressedIco.getIconHeight();
		
		//Si button
		siBtn.setSize(width,height);
		siBtn.setLocation(siBtnX,siBtnY);
		siBtn.setIcon(siBtnUnpressedIco);
		siBtn.setPressedIcon(siBtnPressedIco);
		siBtn.setOpaque(false);
		siBtn.setBorder(null);
		siBtn.setContentAreaFilled(false);
		
		//no button
		noBtn.setSize(width,height);
		noBtn.setLocation(noBtnX,noBtnY);
		noBtn.setIcon(noBtnUnpressedIco);
		noBtn.setPressedIcon(noBtnPressedIco);
		noBtn.setOpaque(false);
		noBtn.setBorder(null);
		noBtn.setContentAreaFilled(false);
		
		//ok button
		width = Math.round((float)(okBtnUnpressedIco.getIconWidth())/scale);
		height = Math.round((float)(okBtnUnpressedIco.getIconHeight())/scale);
		okBtnUnpressedIco = new ImageIcon(okBtnUnpressedIco.getImage()
				.getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH));
		okBtnPressedIco = new ImageIcon(okBtnPressedIco.getImage()
				.getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH));
		okBtn.setSize(width,height);
		okBtn.setLocation(okBtnX,okBtnY);
		okBtn.setIcon(okBtnUnpressedIco);
		okBtn.setPressedIcon(okBtnPressedIco);
		okBtn.setOpaque(false);
		okBtn.setBorder(null);
		okBtn.setContentAreaFilled(false);
		
		
		//Backgrounds
		smallWindowBackground.setBounds(0,0,windowWidth,smallWindowHeight);
		messageBoxSmallIco = new ImageIcon(messageBoxSmallIco.getImage()
				.getScaledInstance(smallBackgroundWidth, smallBackgroundHeight,java.awt.Image.SCALE_SMOOTH));
		smallWindowBackground.setIcon(messageBoxSmallIco);
		
		bigWindowBackground.setBounds(0,0,windowWidth,bigWindowHeight);
		bigWindowBackground.setOpaque(false);
		bigWindowBackground.setIcon(messageBoxBigIco);
		seguroQueDeseaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Labels
		int labelHeight = 40;
		int labelUpY = 60;
		seguroQueDeseaLabel.setFont(font);
		seguroQueDeseaLabel.setBounds(0,labelUpY,windowWidth,labelHeight);
		seguroQueDeseaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		eliminarLaEjecucionLabel.setFont(font);
		eliminarLaEjecucionLabel.setBounds(0,labelUpY+labelHeight-6,windowWidth,labelHeight);
		eliminarLaEjecucionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelUpY = Math.round(40*scale);
		contrasenaIncorrectaLabel.setFont(smallFont);
		contrasenaIncorrectaLabel.setBounds(0,labelUpY,smallBackgroundWidth,Math.round(labelHeight/scale));
		contrasenaIncorrectaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		confirmacionIncorrectaLabel.setFont(smallFont);
		confirmacionIncorrectaLabel.setBounds(0,labelUpY,smallBackgroundWidth,Math.round(labelHeight/scale));
		confirmacionIncorrectaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(seguroQueDeseaLabel);
		add(eliminarLaEjecucionLabel);
		add(contrasenaIncorrectaLabel);
		add(confirmacionIncorrectaLabel);
		add(okBtn);
		add(siBtn);
		add(noBtn);
		add(smallWindowBackground);
		add(bigWindowBackground);
	}
	
	public void setSiBtnAccionListener(MouseAdapter mouseAdapter) {
		siBtn.addMouseListener(mouseAdapter);
	}
	
	private void setBigWindow() {
		setSize(windowWidth,bigWindowHeight);
		siBtn.setVisible(true);
		noBtn.setVisible(true);
		bigWindowBackground.setVisible(true);
		okBtn.setVisible(false);
		smallWindowBackground.setVisible(false);
	}
	
	private void setSmallWindow() {
		setLocation(680,60);
		setSize(windowWidth,smallWindowHeight);
		siBtn.setVisible(false);
		noBtn.setVisible(false);
		bigWindowBackground.setVisible(false);
		okBtn.setVisible(true);
		smallWindowBackground.setVisible(true);
	}
	
	public void executionEliminationMessage(int screenWidth,int screenHeight) {
		setLocation(Math.round(screenWidth/2)-Math.round(windowWidth/2)
				,Math.round(screenHeight/2)-Math.round(bigWindowHeight/2));
		setBigWindow();
		seguroQueDeseaLabel.setVisible(true);
		eliminarLaEjecucionLabel.setVisible(true);
		contrasenaIncorrectaLabel.setVisible(false);
		confirmacionIncorrectaLabel.setVisible(false);
		setVisible(true);
	}
	
	public void incorrectPasswordMessage() {
		setSmallWindow();
		seguroQueDeseaLabel.setVisible(false);
		eliminarLaEjecucionLabel.setVisible(false);
		contrasenaIncorrectaLabel.setVisible(true);
		confirmacionIncorrectaLabel.setVisible(false);
		setVisible(true);
	}
	
	public void incorrectConfirmationMessage() {
		setSmallWindow();
		seguroQueDeseaLabel.setVisible(false);
		eliminarLaEjecucionLabel.setVisible(false);
		contrasenaIncorrectaLabel.setVisible(false);
		confirmacionIncorrectaLabel.setVisible(true);
		setVisible(true);
	}

}
