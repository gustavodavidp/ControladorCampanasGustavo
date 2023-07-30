package visual_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import useful_classes.osChange;

public class SelectDayHourPanel extends JPanel {
	//JPanel basePane = new JPanel();
	JLabel title = new JLabel("Seleccione los dias y la hora");
	JLabel hour = new JLabel();
	JLabel minute = new JLabel();
	JLabel section = new JLabel();
	JLabel colon = new JLabel(":");
	String baseHour = "01";
	String baseMinutes = "00";
	String baseSection = "p.m.";
	JPanelBackground basePane = new JPanelBackground();
	VirtualNumberKeyboard numKey = new VirtualNumberKeyboard(400,305,40);
	
	ImageIcon baseIco = new ImageIcon(this.getClass().getResource("/select_day_hour_base.png"));
	int baseWidth;
	int baseHeight;
	int baseY;
	int scale = 2;
	public JLabel[] hourData = new JLabel[] {
			hour,minute,section
	};
	String selectedDaysOfWeek="";
	MatteBorder border = new MatteBorder(3, 0, 4, 0, new Color(14,160,250,200));
	osChange os = new osChange();
	MainPane main;
	
	private class DayButton extends JPanel{
		private int x;
		private int y;
		private int width;
		private int height;
		private String dayNum="";
		private String temporalDayNum;
		private int textSize = 30;
		private boolean selected = false;
		private Color pressedColor = new Color(37,103,174,250);
		private Color unpressedColor = new Color(37,103,174,89);
		private JLabel pressedLabel = new JLabel();
		private JLabel unpressedLabel = new JLabel();
		private JLabel text = new JLabel();
		
		public void setText(String text) {
			this.text.setText(text);
		}
		
		public boolean getSelected() {
			return selected;
		}
		
		public String getDayNum() {
			return dayNum;
		}
		
		public void reset() {
			unpressedLabel.setVisible(true);
			pressedLabel.setVisible(false);
			selected=false;
			dayNum = "";
		}
		
		private void changeBackground() {
			if(selected) {
				unpressedLabel.setVisible(false);
				pressedLabel.setVisible(true);
			}
			else{
				unpressedLabel.setVisible(true);
				pressedLabel.setVisible(false);
			}
		}
		
		public DayButton(int x, int y, int width, int height,int num){
			this.temporalDayNum = String.valueOf(num);
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			setBounds(x,y,width,height);
			setOpaque(false);
			setBorder(null);
			setLayout(null);
			//text setting
			text.setBounds(0,0,width,height);
			text.setHorizontalAlignment(SwingConstants.CENTER);
			text.setVerticalAlignment(SwingConstants.CENTER);
			text.setForeground(Color.WHITE);
			text.setFont(new Font("Alegreya Sans SC Medium", Font.PLAIN, textSize));
			//Color labels
			pressedLabel.setBackground(pressedColor);
			pressedLabel.setVisible(false);
			pressedLabel.setBounds(text.getBounds());
			pressedLabel.setOpaque(true);
			unpressedLabel.setBackground(unpressedColor);
			unpressedLabel.setVisible(true);
			unpressedLabel.setBounds(text.getBounds());
			unpressedLabel.setOpaque(true);
			
			add(text);
			add(pressedLabel);
			add(unpressedLabel);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selected = !selected;
					dayNum = selected? temporalDayNum:"";
					changeBackground();
					//System.out.println("background changed");
				}
				
			});
		}
	}
	
	DayButton[] days = new DayButton[7];
	String[] dayNames = new String[] {
		"Dom","Lun","Mar","Mie","Jue","Vie","Sáb"
	};
	public SelectDayHourPanel() {
		//Setting size parameters
				//Screen
				Dimension screenSize = os.setDimension();
				int screenWidth = (int)screenSize.getWidth();
				int screenHeight = (int)screenSize.getHeight();
				//Panel
				int panelWidth = 1000;
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
				baseY = (panelHeight/2) - (baseHeight/2)-110;
				//baseIco = new ImageIcon(baseIco.getImage()
				//		.getScaledInstance(baseWidth, baseHeight,java.awt.Image.SCALE_SMOOTH));
				basePane.setBounds((panelWidth/2)-(baseWidth/2),baseY,baseWidth,baseHeight);
				String imageDir = "/select_day_hour_base.png";
				basePane.setBackground(imageDir);
				basePane.setLayout(null);
				
				//Title setting
				title.setBounds(10,10,500,35);
				//title.setOpaque(true);
				title.setFont(new Font("Alegreya Sans SC Medium", Font.PLAIN, 40));
				title.setForeground(Color.BLACK);
				basePane.add(title);
				
				//DayButtons
				int outerGap = 10;
				int innerGap = 10;
				int btnY=50;
				int btnX;
				int btnWidth=(int)Math.round((baseWidth-2*outerGap-6*innerGap)*1f/7*1f);
				int btnHeight=60;
				
				for(int i=0;i<7;i++) {
					btnX = outerGap+(btnWidth+innerGap)*i;
					 days[i] = new DayButton(btnX,btnY,btnWidth,btnHeight,i+1);
					 days[i].setText(dayNames[i]);
					 basePane.add(days[i]);
				}
				
				//Hour setting
				boolean debugOpaque = false;
				int numberWidth = 80;
				int sectionWidth = 145;
				int sectionGap = 10;
				int colonWidth = 50;
				int gap = 0;
				int componentHeight = 70;
				int componentsX = (baseWidth/2)-(numberWidth+gap+colonWidth+gap+numberWidth+sectionGap+sectionWidth)/2;
				int componentsY = 125;
				int hourSize = 60;
				//Hour
				hourData[0].setText(baseHour);
				hourData[0].setBounds(componentsX,componentsY,numberWidth,componentHeight);
				hourData[0].setFont(new Font("DejaVu Sans Mono", Font.PLAIN, hourSize));
				hourData[0].setOpaque(debugOpaque);
				hourData[0].setBackground(Color.RED);
				hourData[0].setHorizontalAlignment(SwingConstants.RIGHT);
				basePane.add(hourData[0]);
				//colon
				colon.setBounds(hour.getLocation().x+numberWidth+gap,componentsY,colonWidth,componentHeight);
				colon.setHorizontalAlignment(SwingConstants.CENTER);
				colon.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, hourSize));
				colon.setOpaque(debugOpaque);
				colon.setBackground(Color.RED);
				basePane.add(colon);
				//minutes
				hourData[1].setText(baseMinutes);
				hourData[1].setBounds(colon.getLocation().x+colonWidth+gap,componentsY,numberWidth,componentHeight);
				hourData[1].setHorizontalAlignment(SwingConstants.LEFT);
				hourData[1].setOpaque(debugOpaque);
				hourData[1].setFont(new Font("DejaVu Sans Mono", Font.PLAIN, hourSize));
				hourData[1].setBackground(Color.RED);
				basePane.add(hourData[1]);
				//section
				hourData[2].setText(baseSection);
				hourData[2].setBounds(minute.getLocation().x+numberWidth+sectionGap,componentsY,sectionWidth,componentHeight);
				hourData[2].setHorizontalAlignment(SwingConstants.LEFT);
				hourData[2].setOpaque(debugOpaque);
				hourData[2].setFont(new Font("DejaVu Sans Mono", Font.PLAIN, hourSize));
				hourData[2].setBackground(Color.RED);
				basePane.add(hourData[2]);
				
				hourData[0].setBorder(border);
				numKey.setParent(this);
				
				add(basePane);
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
	
	public void setMainPane(MainPane main) {
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
	void fillSelectedWeekDaysWord() {
		for(DayButton day: days) {
			selectedDaysOfWeek += day.getDayNum();
		}
	}
	void resetWeekDaySelection() {
		for(DayButton day: days) {
			day.reset();
		}
		selectedDaysOfWeek = "";
	}
	void ready() {
		fillSelectedWeekDaysWord();
		//System.out.println("selectedDaysOfWeek: "+selectedDaysOfWeek);
		if(!selectedDaysOfWeek.equals("")) {
			main.daysOfWeekForMessage = selectedDaysOfWeek;
			selectedDaysOfWeek="";
			main.hourForMessage=hourData[0].getText()+":"+hourData[1].getText()+hourData[2].getText();
			//System.out.println(main.message);
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
			resetWeekDaySelection();
			main.selectExecutionPane.cleanButtonList();
			main.selectExecutionPane.showButtonListAndSelectionSetting();
			main.menuNavegation.next(main.atribute);
		}
	}
}
