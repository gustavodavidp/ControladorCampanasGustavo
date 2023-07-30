package visual_classes;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import useful_classes.MenuOptionsType;
import useful_classes.osChange;

public class ExecutionTypePanel extends JPanel {
	JButton toques_btn;
	JButton carrillon_btn;
	JButton bandeo_btn;
	osChange os = new osChange();
	MainPane main;
	
	public ExecutionTypePanel() {
		//Setting size parameters
		//Screen
		Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		//Panel
		int panelWidth = 700;
		int panelHeight = screenHeight;
		int panelX=(screenWidth/2)-(panelWidth/2);
		int panelY=0;
		
		//Setting this panel
		setOpaque(false);
		setBounds(panelX, panelY, panelWidth, panelHeight);
		setLayout(null);
		
		int width;
		int height;
		float scale = 3.2f;
		int gap = 30;
		
		ImageIcon icon[] = new ImageIcon[6];
		icon[0] = new ImageIcon(this.getClass().getResource("/toques_btn_unpressed.png"));
		icon[1] = new ImageIcon(this.getClass().getResource("/carrillon_btn_unpressed.png"));
		icon[2] = new ImageIcon(this.getClass().getResource("/bandeo_btn_unpressed.png"));
		icon[3] = new ImageIcon(this.getClass().getResource("/toques_btn_pressed.png"));
		icon[4] = new ImageIcon(this.getClass().getResource("/carrillon_btn_pressed.png"));
		icon[5] = new ImageIcon(this.getClass().getResource("/bandeo_btn_pressed.png"));
		
		width = (int)(icon[0].getIconWidth()/scale);
		height = (int)(icon[0].getIconHeight()/scale);
	
		for(int i=0;i<6;i++)
			icon[i] = new ImageIcon(icon[i].getImage().getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH));
		
	
		
		int buttonX = (panelWidth/2)-(width/2);
		int btn1Y = (getHeight()/2)-(height*3+gap*2)/2;
		int btn2Y = btn1Y + gap + height;
		int btn3Y = btn2Y+ gap + height;
		
	/*	System.out.println("btn1Y: "+btn1Y+", btn2Y: "+btn2Y+", btn3Y: "+btn3Y);
		System.out.println("Button width: "+width);
		System.out.println("Button height: "+height);*/
		
		//Buttons declaration
		//Toque button
		toques_btn = new JButton();
		toques_btn.setBounds(buttonX, btn1Y, width, height);
		toques_btn.setBorder(null);
		toques_btn.setContentAreaFilled(false);
		//Carrillon button
		carrillon_btn = new JButton();
		carrillon_btn.setBounds(buttonX, btn2Y, width, height);
		carrillon_btn.setBorder(null);
		carrillon_btn.setContentAreaFilled(false);
		//Bandeo button
		bandeo_btn = new JButton();
		bandeo_btn.setBounds(buttonX, btn3Y, width, height);
		bandeo_btn.setBorder(null);
		bandeo_btn.setContentAreaFilled(false);
		
		//Setting button icons
		//Toque button
		toques_btn.setIcon(icon[0]);
		toques_btn.setPressedIcon(icon[3]);
		
		//Carrillon button
		carrillon_btn.setIcon(icon[1]);
		carrillon_btn.setPressedIcon(icon[4]);
		
		//Bandeo button
		bandeo_btn.setIcon(icon[2]);
		bandeo_btn.setPressedIcon(icon[5]);
	
		
	
		toques_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("Click en toques");
			}
		});
		
		//Buttons and labels adding
		add(toques_btn);
		add(carrillon_btn);
		add(bandeo_btn);

	}
	public void setMainPane(MainPane main) {
		this.main = main;
		setActions();
	}
	private void setActions() {
		toques_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				main.enumType = MenuOptionsType.TOQUES;
				main.atribute.type = main.enumType;
				main.menuNavegation.next(main.atribute);
			}
		});
		carrillon_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				main.enumType = MenuOptionsType.CARRILLON;
				main.atribute.type = main.enumType;
				main.menuNavegation.next(main.atribute);
			}
		});
		bandeo_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				main.enumType = MenuOptionsType.BANDEO;
				main.atribute.type = main.enumType;
				main.menuNavegation.next(main.atribute);
			}
		});
	}

}
