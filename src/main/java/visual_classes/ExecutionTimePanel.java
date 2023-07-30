package visual_classes;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import useful_classes.MenuOptionsTime;
import useful_classes.osChange;

public class ExecutionTimePanel extends JPanel {
	JButton inmediatas_btn;
	JButton programadas_btn;
	JButton repetitivas_btn;
	osChange os = new osChange();
	MainPane main;
	
	public ExecutionTimePanel() {
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
		icon[0] = new ImageIcon(this.getClass().getResource("/inmediatas_btn_unpressed.png"));
		icon[1] = new ImageIcon(this.getClass().getResource("/programadas_btn_unpressed.png"));
		icon[2] = new ImageIcon(this.getClass().getResource("/repetitivas_btn_unpressed.png"));
		icon[3] = new ImageIcon(this.getClass().getResource("/inmediatas_btn_pressed.png"));
		icon[4] = new ImageIcon(this.getClass().getResource("/programadas_btn_pressed.png"));
		icon[5] = new ImageIcon(this.getClass().getResource("/repetitivas_btn_pressed.png"));
		
		width = (int)(icon[0].getIconWidth()/scale);
		height = (int)(icon[0].getIconHeight()/scale);
	
		for(int i=0;i<6;i++)
			icon[i] = new ImageIcon(icon[i].getImage().getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH));
		
	
		
		int buttonX = (panelWidth/2)-(width/2);
		int btn1Y = (getHeight()/2)-(height*3+gap*2)/2;
		int btn2Y = btn1Y + gap + height;
		int btn3Y = btn2Y+ gap + height;
		
		//Buttons declaration
		//Toque button
		inmediatas_btn = new JButton();
		inmediatas_btn.setBounds(buttonX, btn1Y, width, height);
		inmediatas_btn.setBorder(null);
		inmediatas_btn.setContentAreaFilled(false);
		//Carrillon button
		programadas_btn = new JButton();
		programadas_btn.setBounds(buttonX, btn2Y, width, height);
		programadas_btn.setBorder(null);
		programadas_btn.setContentAreaFilled(false);
		//Bandeo button
		repetitivas_btn = new JButton();
		repetitivas_btn.setBounds(buttonX, btn3Y, width, height);
		repetitivas_btn.setBorder(null);
		repetitivas_btn.setContentAreaFilled(false);
		
		//Setting button icons
		//Toque button
		inmediatas_btn.setIcon(icon[0]);
		inmediatas_btn.setPressedIcon(icon[3]);
		
		//Carrillon button
		programadas_btn.setIcon(icon[1]);
		programadas_btn.setPressedIcon(icon[4]);
		
		//Bandeo button
		repetitivas_btn.setIcon(icon[2]);
		repetitivas_btn.setPressedIcon(icon[5]);
	
		
	
		inmediatas_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("Click en toques");
			}
		});
		
		//Buttons and labels adding
		add(inmediatas_btn);
		add(programadas_btn);
		add(repetitivas_btn);

	}
	public void setMainPane(MainPane main) {
		this.main = main;
		setActions();
	}
	private void setActions() {
		inmediatas_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();

				switch(main.atribute.type) {
				case TOQUES:
					main.selectExecutionPane.setType("el toque");
					main.selectExecutionPane.setExtensionNameList(".toc");
					break;
				case CARRILLON:
					main.selectExecutionPane.setType("la melodía");
					main.selectExecutionPane.setExtensionNameList(".mp3");
					break;
				case BANDEO:
					main.selectExecutionPane.setType("la secuencia");
					main.selectExecutionPane.setExtensionNameList(".sec");
					break;
				}
				main.selectExecutionPane.cleanButtonList();
				main.selectExecutionPane.showButtonListAndSelectionSetting();
				main.enumTime = MenuOptionsTime.INMEDIATAS;
				main.atribute.time = main.enumTime;
				main.selectExecutionPane.anExecutionIsSelected = false;
				main.menuNavegation.next(main.atribute);
			}
		});
		programadas_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				main.selectDatePane.showCalendar();
				main.atribute.time = MenuOptionsTime.PROGRAMADAS;
				main.menuNavegation.next(main.atribute);
			}
		});
		repetitivas_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				main.atribute.time = MenuOptionsTime.REPETITIVAS;
				main.menuNavegation.next(main.atribute);
			}
		});
	}

}
