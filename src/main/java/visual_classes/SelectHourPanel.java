package visual_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import useful_classes.osChange;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectHourPanel extends JPanel {
	//JPanel basePane = new JPanel();
	JLabel title = new JLabel("Seleccione la hora");
	JLabel hour = new JLabel();
	JLabel minute = new JLabel();
	JLabel section = new JLabel();
	JLabel colon = new JLabel(":");
	String baseHour = "01";
	String baseMinutes = "00";
	String baseSection = "p.m.";
	JPanelBackground basePane = new JPanelBackground();
	VirtualNumberKeyboard numKey = new VirtualNumberKeyboard();
	
	ImageIcon baseIco = new ImageIcon(this.getClass().getResource("/select_hour_base.png"));
	int baseWidth;
	int baseHeight;
	int baseY;
	int scale = 4;
	public JLabel[] hourData = new JLabel[] {
			hour,minute,section
	};
	MatteBorder border = new MatteBorder(3, 0, 4, 0, new Color(14,160,250,200));
	MainPane main;
	osChange os = new osChange();
	
	public SelectHourPanel() {
		//Setting size parameters
		//Screen
		Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		//Panel
		int panelWidth = 800;
		int panelHeight = screenHeight;
		int panelX=(screenWidth/2)-(panelWidth/2);
		int panelY=0;
		
		//Setting this panel
		setOpaque(false);
		setBounds(panelX, panelY, panelWidth, panelHeight);
		setLayout(null);
		setBackground(Color.RED);
		
		//Base setting
		baseWidth = (int)Math.round(baseIco.getIconWidth()/scale);
		baseHeight = (int)Math.round(baseIco.getIconHeight()/scale);
		baseY = (panelHeight/2) - (baseHeight/2)-150;
		//baseIco = new ImageIcon(baseIco.getImage()
		//		.getScaledInstance(baseWidth, baseHeight,java.awt.Image.SCALE_SMOOTH));
		basePane.setBounds((panelWidth/2)-(baseWidth/2),baseY,baseWidth,baseHeight);
		String imageDir = "/select_hour_base.png";
		basePane.setBackground(imageDir);
		basePane.setLayout(null);
		
		//Title setting
		title.setBounds(30,20,320,35);
		//title.setOpaque(true);
		title.setFont(new Font("Alegreya Sans SC Medium", Font.PLAIN, 40));
		title.setForeground(Color.WHITE);
		basePane.add(title);
		
		//Hour setting
		boolean debugOpaque = false;
		int numberWidth = 102;
		int sectionWidth = 195;
		int sectionGap = 10;
		int colonWidth = 50;
		int gap = 0;
		int componentHeight = 100;
		int componentsX = (baseWidth/2)-(numberWidth+gap+colonWidth+gap+numberWidth+sectionGap+sectionWidth)/2;
		int componentsY = 70;
		//Hour
		hourData[0].setText(baseHour);
		hourData[0].setBounds(componentsX,componentsY,numberWidth,componentHeight);
		hourData[0].setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 80));
		hourData[0].setOpaque(debugOpaque);
		hourData[0].setBackground(Color.RED);
		hourData[0].setHorizontalAlignment(SwingConstants.RIGHT);
		basePane.add(hourData[0]);
		//colon
		colon.setBounds(hour.getLocation().x+numberWidth+gap,componentsY,colonWidth,componentHeight);
		colon.setHorizontalAlignment(SwingConstants.CENTER);
		colon.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 80));
		colon.setOpaque(debugOpaque);
		colon.setBackground(Color.RED);
		basePane.add(colon);
		//minutes
		hourData[1].setText(baseMinutes);
		hourData[1].setBounds(colon.getLocation().x+colonWidth+gap,componentsY,numberWidth,componentHeight);
		hourData[1].setHorizontalAlignment(SwingConstants.LEFT);
		hourData[1].setOpaque(debugOpaque);
		hourData[1].setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 80));
		hourData[1].setBackground(Color.RED);
		basePane.add(hourData[1]);
		//section
		hourData[2].setText(baseSection);
		hourData[2].setBounds(minute.getLocation().x+numberWidth+sectionGap,componentsY,sectionWidth,componentHeight);
		hourData[2].setHorizontalAlignment(SwingConstants.LEFT);
		hourData[2].setOpaque(debugOpaque);
		hourData[2].setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 80));
		hourData[2].setBackground(Color.RED);
		basePane.add(hourData[2]);
		
		hourData[0].setBorder(border);
		numKey.setParent(this);
		add(basePane);
		
		/*JLabel lblNewLabel = new JLabel("holaa");
		lblNewLabel.setBorder(new MatteBorder(3, 0, 3, 0, (Color) Color.BLUE));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(0, 0, 46, 14);
		basePane.add(lblNewLabel);*/
		add(numKey);
		
		for(JLabel data: hourData) {
			data.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for(int i=0;i<3;i++) {
						if(data.equals(hourData[i])) {
							numKey.select=i;
							selectChange();
							break;
						}
					}
				}
			});
		}
	}
	void setMainPane(MainPane main){
		this.main = main;
	}
	
	void selectChange() {
		for(int i=0;i<3;i++) {
			if(i==numKey.select) {
				hourData[i].setBorder(border);
			}
			else
				hourData[i].setBorder(null);
		}
	}
	
	void ready() {
		main.hourForMessage = hourData[0].getText()+":"+hourData[1].getText()+hourData[2].getText();
		///System.out.println(main.hourForMessage);
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
		hourData[0].setText(baseHour);
		hourData[1].setText(baseMinutes);
		hourData[2].setText(baseSection);
		numKey.select=0;
		selectChange();
		main.selectExecutionPane.cleanButtonList();
		main.selectExecutionPane.showButtonListAndSelectionSetting();
		main.menuNavegation.next(main.atribute);
	}
}
