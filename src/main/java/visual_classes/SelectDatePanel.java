package visual_classes;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import useful_classes.osChange;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectDatePanel extends JPanel {
	MainPane main;
	JPanel calendar = new JPanel();
	JLabel calendarBase = new JLabel();
	JPanel calendarDaysPane = new JPanel();
	JPanel calendarTitle = new JPanel();
	JLabel calendarMonthYear = new JLabel();
	JLabel leftArrow = new JLabel();
	JLabel rightArrow = new JLabel();
	JLabel bigRightArrow = new JLabel();
	JLabel bigLeftArrow = new JLabel();
	JLabel lastPressed;
	ImageIcon lastIcon;
	JButton siguienteBtn = new JButton();
	int calendarY = 90;
	int actualMonth;
	int selectedMonth=0;
	int actualDay;
	String actualYear;
	String selectedYear="";
	
	ImageIcon siguienteIconUnpressed = new ImageIcon(this.getClass().getResource("/siguiente_btn_unpressed.png"));
	ImageIcon siguienteIconPressed = new ImageIcon(this.getClass().getResource("/siguiente_btn_pressed.png"));
	int siguienteBtnWidth;
	int siguienteBtnHeight;
	//JLabel calendarDayLabel = new JLabel();
	ArrayList<JPanel> calendarDays = new ArrayList<JPanel>();
	
	//CalendarDayConfig
	float scale = 4f;
	int sideGap = (int)Math.round(60/scale);
	int upperGap = (int)Math.round(403/scale);
	int horizontalGap = (int)Math.round(24/scale);
	int verticalGap = (int)Math.round(20/scale);
	//TItle
	String DateTitleText;
	
	//Image icons
	ImageIcon calendarBaseIco = new ImageIcon(this.getClass().getResource("/calendar_base.png"));
	ImageIcon calendarDayUnpressedIco = new ImageIcon(this.getClass().getResource("/calendar_day_unpressed.png"));
	ImageIcon calendarDayPressedIco = new ImageIcon(this.getClass().getResource("/calendar_day_pressed.png"));
	ImageIcon calendarDayOtherIco = new ImageIcon(this.getClass().getResource("/calendar_day_other.png"));
	ImageIcon calendarDoubleDayUnpressedIco = new ImageIcon(this.getClass().getResource("/calendar_double_day_unpressed.png"));
	ImageIcon calendarDoubleDayPressedIco = new ImageIcon(this.getClass().getResource("/calendar_double_day_pressed.png"));
	ImageIcon calendarUpDaySelectedIco = new ImageIcon(this.getClass().getResource("/calendar_up_day_selected.png"));
	ImageIcon calendarDownDaySelectedIco = new ImageIcon(this.getClass().getResource("/calendar_down_day_selected.png"));
	ImageIcon calendarLeftArrowUnpressedIco = new ImageIcon(this.getClass().getResource("/left_arrow_unpressed.png"));
	ImageIcon calendarRightArrowUnpressedIco = new ImageIcon(this.getClass().getResource("/right_arrow_unpressed.png"));
	ImageIcon calendarLeftArrowPressedIco = new ImageIcon(this.getClass().getResource("/left_arrow_unpressed.png"));
	ImageIcon calendarRightArrowPressedIco = new ImageIcon(this.getClass().getResource("/right_arrow_unpressed.png"));
	ImageIcon calendarLeftArrowBloquedIco = new ImageIcon(this.getClass().getResource("/left_arrow_bloqued.png"));
	
	ImageIcon bigCalendarLeftArrowUnpressedIco = new ImageIcon(this.getClass().getResource("/left_arrow_unpressed.png"));
	ImageIcon bigCalendarRightArrowUnpressedIco = new ImageIcon(this.getClass().getResource("/right_arrow_unpressed.png"));
	ImageIcon bigCalendarLeftArrowPressedIco = new ImageIcon(this.getClass().getResource("/left_arrow_unpressed.png"));
	ImageIcon bigCalendarRightArrowPressedIco = new ImageIcon(this.getClass().getResource("/right_arrow_unpressed.png"));
	ImageIcon bigCalendarLeftArrowBloquedIco  = new ImageIcon(this.getClass().getResource("/left_arrow_bloqued.png"));
	
	int calendarBaseWidth;
	int calendarBaseHeight;
	
	int reDimensionAdd;	
	int bigCalendarLeftArrowUnpressedWidth;
	int bigCalendarLeftArrowUnpressedHeight;
	int bigCalendarRightArrowUnpressedWidth;
	int bigCalendarRightArrowUnpressedHeight;
	int bigCalendarLeftArrowPressedWidth;
	int bigCalendarLeftArrowPressedHeight;
	int bigCalendarRightArrowPressedWidth;
	int bigCalendarRightArrowPressedHeight;
	//
	int calendarDayWidth;
	int calendarDayHeight;
	
	//Days
	int day;
	int month;
	int year;
	int numberOfDays;
	int lastMonthNumberOfDays;
	int startingWeekDay;
	int endingWeekDay;
	int daysBeforeMonth;
	int daysAfterMonth;
	int overlapedDays;
	int dayCount;
	int otherMonthDayCount;
	int[] otherMonthDays;
	
	int calendarMonthYearWidth;
	int calendarMonthYearHeight;
	int titleIconsGap;
	int arrowsY;
	int calendarMonthYearY;
	int calendarMonthYearX;
	boolean startToWriteDay;
	boolean blockPreviousMonths = true;
	osChange os = new osChange();
	
	int panelWidth;
	int panelHeight;
	int panelX;
	int panelY;
	float bigScale = 1.5f;
	int bigArrowsY;
	int bigArrowsRelativeX;
	int bigRightArrowsX;
	
	JLabel dayBtn;
	JLabel multiBtn;
	JLabel calendarDayLabel;
	JLabel overlapedCalendarDayLabel;
	JLabel otherDay;
	JPanel dayPane;
	String message="";
	int count=0;
	/**
	 * Create the panel.
	 */
	public SelectDatePanel() {
		//Setting size parameters
		//Screen
		Dimension screenSize = os.setDimension();
		int screenWidth = (int)screenSize.getWidth();
		int screenHeight = (int)screenSize.getHeight();
		
		//Panel
		panelWidth = screenWidth;
		panelHeight = screenHeight;
		panelX=(screenWidth/2)-(panelWidth/2);
		panelY=0;
		
		//Setting this panel
		setOpaque(false);
		setBounds(panelX, panelY, panelWidth, panelHeight);
		setLayout(null);
		setBackground(Color.RED);
		
		setIcons();
		setCalendarTitle();
		setCalendarBase();
		
		add(bigLeftArrow);
		add(bigRightArrow);
			
		calendar.setBackground(Color.BLUE);
		calendar.setLayout(null);
		calendar.setOpaque(false);
		calendar.setBounds((panelWidth/2)-(calendarBaseWidth/2),calendarY,calendarBaseWidth,calendarBaseHeight);
		calendar.add(calendarTitle);
		
		calendarDaysPane.setSize(calendar.getSize());
		calendarDaysPane.setLocation(0,0);
		calendarDaysPane.setLayout(null);
		calendarDaysPane.setOpaque(false);
		
		calendar.add(calendarDaysPane);
		createCalendar();
		setActionsToCalendar();
		calendar.add(calendarBase);
		
		float scale = 1.5f;
		siguienteBtnWidth = Math.round(150f*scale);
		siguienteBtnHeight = Math.round(40f*scale);
		int siguienteBtnX = (panelWidth/2)-(siguienteBtnWidth/2);
		int siguenteBtnY = calendar.getY()+calendar.getHeight()+15;
		siguienteIconUnpressed = new ImageIcon(siguienteIconUnpressed.getImage().getScaledInstance(siguienteBtnWidth, siguienteBtnHeight,java.awt.Image.SCALE_SMOOTH));
		siguienteIconPressed = new ImageIcon(siguienteIconPressed.getImage().getScaledInstance(siguienteBtnWidth, siguienteBtnHeight,java.awt.Image.SCALE_SMOOTH));
		siguienteBtn.setBounds(siguienteBtnX, siguenteBtnY, siguienteBtnWidth, siguienteBtnHeight);
		siguienteBtn.setIcon(siguienteIconUnpressed);
		siguienteBtn.setPressedIcon(siguienteIconPressed);
		siguienteBtn.setBorder(null);
		siguienteBtn.setContentAreaFilled(false);
		add(siguienteBtn);
		
		add(calendar);
	}
	
	public void setMainPane(MainPane main) {
		this.main = main;
		setActions();
	}
	
	public void setActions() {
		siguienteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!message.equals("")) {
					main.dateForMessage = message;
					//System.out.println(main.dateForMessage);
					main.menuNavegation.next(main.atribute);
				}
				message="";
				lastPressed.setIcon(lastIcon);
			}
		});
		
		
		bigLeftArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!blockPreviousMonths) {
					selectedMonth = previousMonth(selectedMonth,false);
					setDateTitleText(false);
					calendarMonthYear.setText(DateTitleText);
					startToWriteDay = false;
					message="";
					monthCalendarDataSetting();	
					otherMonthDaysFill();
					fillCalendar();
				}
				updatePreviousMonthBlocking();
				if(blockPreviousMonths)
					bigLeftArrow.setIcon(bigCalendarLeftArrowBloquedIco);
				else
					bigLeftArrow.setIcon(bigCalendarLeftArrowUnpressedIco);
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				int reLocate = (int)Math.round((reDimensionAdd*1f)/(2*1f));
				bigLeftArrow.setBounds(bigArrowsRelativeX-reLocate,bigArrowsY-reLocate,bigCalendarLeftArrowPressedWidth,bigCalendarLeftArrowPressedHeight);
				if(!blockPreviousMonths)
					bigLeftArrow.setIcon(bigCalendarLeftArrowPressedIco);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				bigLeftArrow.setBounds(bigArrowsRelativeX,bigArrowsY,bigCalendarLeftArrowUnpressedWidth,bigCalendarLeftArrowUnpressedHeight);
				if(!blockPreviousMonths)
					bigLeftArrow.setIcon(bigCalendarLeftArrowUnpressedIco);
			}
		});
		bigRightArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedMonth = nextMonth(selectedMonth);
				setDateTitleText(false); //boolean useActual
				calendarMonthYear.setText(DateTitleText);
				startToWriteDay = false;
				message="";
				monthCalendarDataSetting();	
				otherMonthDaysFill();
				fillCalendar();
				updatePreviousMonthBlocking();
				if(blockPreviousMonths)
					bigLeftArrow.setIcon(bigCalendarLeftArrowBloquedIco);
				else
					bigLeftArrow.setIcon(bigCalendarLeftArrowUnpressedIco);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				int reLocate = (int)Math.round((reDimensionAdd*1f)/(2*1f));
				bigRightArrow.setBounds(bigRightArrowsX-reLocate,bigArrowsY-reLocate,bigCalendarRightArrowPressedWidth,bigCalendarRightArrowPressedHeight);
				bigRightArrow.setIcon(bigCalendarRightArrowPressedIco);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				bigRightArrow.setBounds(bigRightArrowsX,bigArrowsY,bigCalendarRightArrowUnpressedWidth,bigCalendarRightArrowUnpressedHeight);
				bigRightArrow.setIcon(bigCalendarRightArrowUnpressedIco);
			}
		});
	}
	public void clean() {
		
	}
	public void showCalendar() {
		//Update date info
		getActualDate();
		setDateTitleText(true); //boolean useActual
		calendarMonthYear.setText(DateTitleText);
		
		startToWriteDay = false;
		monthCalendarDataSetting();	
		otherMonthDaysFill();
		fillCalendar();
	}
	private void setIcons(){
		calendarBaseWidth = (int)Math.round(calendarBaseIco.getIconWidth()/scale);
		calendarBaseHeight = (int)Math.round(calendarBaseIco.getIconHeight()/scale);
		calendarBaseIco = new ImageIcon(calendarBaseIco.getImage()
				.getScaledInstance(calendarBaseWidth, calendarBaseHeight,java.awt.Image.SCALE_SMOOTH));
		calendarDayWidth = (int)Math.round(calendarDayUnpressedIco.getIconWidth()/scale);
		calendarDayHeight = (int)Math.round(calendarDayUnpressedIco.getIconHeight()/scale);
		calendarDayUnpressedIco = new ImageIcon(calendarDayUnpressedIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));
		calendarDayPressedIco = new ImageIcon(calendarDayPressedIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));
		calendarDoubleDayUnpressedIco = new ImageIcon(calendarDoubleDayUnpressedIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));
		calendarDoubleDayPressedIco = new ImageIcon(calendarDoubleDayPressedIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));
		calendarDayOtherIco = new ImageIcon(calendarDayOtherIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));
		calendarUpDaySelectedIco = new ImageIcon(calendarUpDaySelectedIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));
		calendarDownDaySelectedIco = new ImageIcon(calendarDownDaySelectedIco.getImage()
				.getScaledInstance(calendarDayWidth, calendarDayHeight,java.awt.Image.SCALE_SMOOTH));

		bigCalendarLeftArrowUnpressedWidth = (int)Math.round(bigCalendarLeftArrowUnpressedIco.getIconWidth()/bigScale);
		bigCalendarLeftArrowUnpressedHeight = (int)Math.round(bigCalendarLeftArrowUnpressedIco.getIconHeight()/bigScale);
		bigCalendarLeftArrowUnpressedIco = new ImageIcon(bigCalendarLeftArrowUnpressedIco.getImage()
				.getScaledInstance(bigCalendarLeftArrowUnpressedWidth, bigCalendarLeftArrowUnpressedHeight,java.awt.Image.SCALE_SMOOTH));
		bigCalendarRightArrowUnpressedWidth = (int)Math.round(bigCalendarRightArrowUnpressedIco.getIconWidth()/bigScale);
		bigCalendarRightArrowUnpressedHeight = (int)Math.round(bigCalendarRightArrowUnpressedIco.getIconHeight()/bigScale);
		bigCalendarRightArrowUnpressedIco = new ImageIcon(bigCalendarRightArrowUnpressedIco.getImage()
				.getScaledInstance(bigCalendarRightArrowUnpressedWidth, bigCalendarRightArrowUnpressedHeight,java.awt.Image.SCALE_SMOOTH));
		reDimensionAdd = 10;
		bigCalendarLeftArrowPressedWidth = reDimensionAdd+(int)Math.round(bigCalendarLeftArrowPressedIco.getIconWidth()/bigScale);
		bigCalendarLeftArrowPressedHeight = reDimensionAdd+(int)Math.round(bigCalendarLeftArrowPressedIco.getIconHeight()/bigScale);
		bigCalendarLeftArrowPressedIco = new ImageIcon(bigCalendarLeftArrowPressedIco.getImage()
				.getScaledInstance(bigCalendarLeftArrowPressedWidth, bigCalendarLeftArrowPressedHeight,java.awt.Image.SCALE_SMOOTH));
		bigCalendarRightArrowPressedWidth = reDimensionAdd+(int)Math.round(bigCalendarRightArrowPressedIco.getIconWidth()/bigScale);
		bigCalendarRightArrowPressedHeight = reDimensionAdd+(int)Math.round(bigCalendarRightArrowPressedIco.getIconHeight()/bigScale);
		bigCalendarRightArrowPressedIco = new ImageIcon(bigCalendarRightArrowPressedIco.getImage()
				.getScaledInstance(bigCalendarRightArrowPressedWidth, bigCalendarRightArrowPressedHeight,java.awt.Image.SCALE_SMOOTH));
		bigCalendarLeftArrowBloquedIco = new ImageIcon(bigCalendarLeftArrowBloquedIco.getImage()
				.getScaledInstance(bigCalendarLeftArrowUnpressedWidth, bigCalendarLeftArrowUnpressedHeight,java.awt.Image.SCALE_SMOOTH));
	}
	private void setCalendarBase(){
		calendarBase.setIcon(calendarBaseIco);
		calendarBase.setBounds(0,0,calendarBaseWidth,calendarBaseHeight);
	}
	
	private void setCalendarTitle(){
		//Title config
		calendarMonthYearWidth = 190;
		calendarMonthYearHeight = 50;
		titleIconsGap = 5;
		arrowsY = 15;
		calendarMonthYearY = arrowsY-7;
		titleIconsGap = 13;
		calendarMonthYearX = titleIconsGap;
		
		//Arrow's icon
		if(blockPreviousMonths) {
			bigLeftArrow.setIcon(bigCalendarLeftArrowBloquedIco);
		}
		else {
			bigLeftArrow.setIcon(bigCalendarLeftArrowPressedIco);
		}
		bigRightArrow.setIcon(bigCalendarRightArrowUnpressedIco);
		
		
		
		//Arrow's bounds
		bigArrowsY = (panelHeight/2) - (bigCalendarLeftArrowUnpressedHeight/2);
		bigArrowsRelativeX = 20;
		bigRightArrowsX=panelWidth - bigArrowsRelativeX - bigCalendarRightArrowUnpressedWidth;
		//Arrow
		bigLeftArrow.setBounds(bigArrowsRelativeX,bigArrowsY,bigCalendarLeftArrowUnpressedWidth,bigCalendarLeftArrowUnpressedHeight);
		bigRightArrow.setBounds(bigRightArrowsX,bigArrowsY,bigCalendarRightArrowUnpressedWidth,bigCalendarRightArrowUnpressedHeight);
		
		//Calendar title setting
		calendarMonthYear.setBounds(calendarMonthYearX,calendarMonthYearY,calendarMonthYearWidth,calendarMonthYearHeight);
		
		calendarMonthYear.setFont(new Font("Alegreya Sans SC", Font.BOLD, 45));
		calendarMonthYear.setBackground(Color.BLUE);
		calendarMonthYear.setHorizontalAlignment(SwingConstants.CENTER);
				
		calendarTitle.add(leftArrow);
		calendarTitle.add(rightArrow);
		calendarTitle.add(calendarMonthYear);
		calendarTitle.setBounds(0,0,400,80);
		calendarTitle.setBackground(Color.YELLOW);
		calendarTitle.setLayout(null);
		calendarTitle.setOpaque(false);
	}
	private void monthCalendarDataSetting() {
		//System.out.println("Selected month: "+selectedMonth);
		numberOfDays = daysInAMonth(selectedMonth,Integer.parseInt(selectedYear));
		lastMonthNumberOfDays = daysInAMonth(previousMonth(selectedMonth,true),Integer.parseInt(selectedYear));
		startingWeekDay = weekDay(1,selectedMonth,Integer.parseInt(selectedYear));
		endingWeekDay = weekDay(daysInAMonth(selectedMonth,Integer.parseInt(selectedYear)),selectedMonth,Integer.parseInt(selectedYear));
		daysBeforeMonth = startingWeekDay-1;
		//daysAfterMonth = 7-endingWeekDay;
		daysAfterMonth = 35 - (daysBeforeMonth + numberOfDays);
		overlapedDays = (daysAfterMonth < 0)? -1*daysAfterMonth:0;
		//System.out.println("overlapedDays: "+overlapedDays);
		dayCount = 1; ////////////////////////////////******
		otherMonthDayCount = 0;
	}
	private void otherMonthDaysFill() {
		daysAfterMonth = Math.max(daysAfterMonth,0);
		otherMonthDays = new int[daysBeforeMonth+daysAfterMonth];
		for(int i=0;i<daysBeforeMonth;i++) {
			otherMonthDays[i] = lastMonthNumberOfDays-daysBeforeMonth+i+1;
		}
		for(int i=0;i<daysAfterMonth;i++) {
			otherMonthDays[daysBeforeMonth+i] = i+1;
		}
	}
	private void getActualDate() {
		Date date = new Date();

		//Da
		DateFormat dayFormat = new SimpleDateFormat("dd");
		actualDay = Integer.parseInt(dayFormat.format(date));
		
		//Mes
		DateFormat monthFormat = new SimpleDateFormat("MM");
		actualMonth = Integer.parseInt(monthFormat.format(date));

		//Ao
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		actualYear = yearFormat.format(date);
		
		selectedMonth = actualMonth;
		selectedYear = actualYear;

	}
		
	private void setDateTitleText(boolean useActual) {
		int monthNum;
		monthNum = useActual? actualMonth:selectedMonth;
		String month = "";
		String year= "";
		switch(monthNum) {
		case 1:
			month = "Ene.";
			break;
		case 2:
			month = "Feb.";
			break;
		case 3:
			month = "Mar.";
			break;
		case 4:
			month = "Abr.";
			break;
		case 5:
			month = "May."; //cambiar a May.
			break;
		case 6:
			month = "Jun.";
			break;
		case 7:
			month = "Jul.";
			break;
		case 8:
			month = "Ago.";
			break;
		case 9:
			month = "Sep.";
			break;
		case 10:
			month = "Oct.";
			break;
		case 11:
			month = "Nov.";
			break;
		case 12:
			month = "Dic.";
			break;
		default:
			month="MES";
		}
		year = useActual? actualYear:selectedYear;
		DateTitleText = month+" "+ year;
	}
	
	int previousMonth(int month,boolean forCalendarSetting) {
		int prevMonth = month-1;
		if(prevMonth == 0) {
			prevMonth = 12;
			if(!forCalendarSetting) selectedYear=Integer.toString(Integer.parseInt(selectedYear)-1);
			}
		return prevMonth;
	}
	int nextMonth(int month) {
		int nextMonth = month+1;
		if (nextMonth == 13) {
			nextMonth = 1;
			selectedYear=Integer.toString(Integer.parseInt(selectedYear)+1);
		}
		return nextMonth;
	}
	
	private void createCalendar(){
		for(int row=0;row<5;row++) {
			for(int column=0;column<7;column++) {			
				dayBtn = new JLabel();
				multiBtn = new JLabel();
				calendarDayLabel = new JLabel();
				overlapedCalendarDayLabel = new JLabel();
				otherDay = new JLabel();
				dayPane = new JPanel();
				
				dayPane.add(calendarDayLabel);
				dayPane.add(overlapedCalendarDayLabel);
				dayPane.add(multiBtn);
				dayPane.add(dayBtn);
				dayPane.add(otherDay);
				calendarDaysPane.add(dayPane);
				//calendar.add(dayPane);
				
				//DayPane config
				int x = sideGap+column*(calendarDayWidth+horizontalGap);
				int y = upperGap+row*(calendarDayHeight+verticalGap);
				dayPane.setBounds(x,y,calendarDayWidth,calendarDayHeight);
				dayPane.setLayout(null);
				dayPane.setOpaque(false);	
				
				//DayBtn config
				dayBtn.setBounds(0,0,calendarDayWidth,calendarDayHeight);
				dayBtn.setOpaque(false);
				dayBtn.setIcon(calendarDayUnpressedIco);
				
				//calendarDayLabel config
				int calendarDayLabelX = 5;
				int calendarDayLabelY = 5;
				calendarDayLabel.setBounds(calendarDayLabelX,calendarDayLabelY,30,20);
				calendarDayLabel.setFont(new Font("Alegreya Sans SC Medium", Font.PLAIN, 25));
				calendarDayLabel.setForeground(Color.BLACK);
				
				//multibtn config
				multiBtn.setBounds(0,0,calendarDayWidth,calendarDayHeight);
				multiBtn.setIcon(calendarDoubleDayUnpressedIco);
				
				//overlapedCalendarDayLabel config
				overlapedCalendarDayLabel.setBounds(calendarDayWidth-calendarDayLabelX-30,calendarDayHeight-calendarDayLabelY-20,30,20);
				overlapedCalendarDayLabel.setForeground(Color.BLACK);
				overlapedCalendarDayLabel.setFont(new Font("Alegreya Sans SC Medium", Font.PLAIN, 25));
				
				//OtherDay config
				otherDay.setBounds(0,0,calendarDayWidth,calendarDayHeight);
				otherDay.setIcon(calendarDayOtherIco);
			}
		}
	}
	private void setActionsToCalendar() {
		count=0;
		JLabel dayBtn;
		JLabel multiBtn;
		for(int row=0;row<5;row++) {
			for(int column=0;column<7;column++) {
				otherDay = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[4];
				dayBtn = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[3];
				multiBtn = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[2];
				overlapedCalendarDayLabel = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[1];
				calendarDayLabel = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[0];
				dayPane = (JPanel) calendarDaysPane.getComponents()[count];
					
				dayBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						JLabel calendarDayLabel = (JLabel) ((JLabel) e.getSource()).getParent().getComponents()[0];
						message = calendarDayLabel.getText()+"-"+selectedMonth+"-"+selectedYear;
						//System.out.println(message);
					}
					@Override
					public void mousePressed(MouseEvent e) {
						if(lastPressed != null)
							lastPressed.setIcon(lastIcon);
						JLabel dayBtn = (JLabel) ((JLabel) e.getSource()).getParent().getComponents()[3];
						lastPressed = dayBtn;
						lastIcon = (ImageIcon) dayBtn.getIcon();
						dayBtn.setIcon(calendarDayPressedIco);
					}
				});
					
				//Overlapped days
				if(row==4) {					
					multiBtn.addMouseListener(new MouseAdapter() {
						boolean up;
						@Override
						public void mouseClicked(MouseEvent e) {
							if(up) {
								
								JLabel calendarDayLabel = (JLabel) ((JLabel) e.getSource()).getParent().getComponents()[0];
								message = calendarDayLabel.getText()+"-"+selectedMonth+"-"+selectedYear;
								//System.out.println(message);
							}
							else {
								JLabel overlapedCalendarDayLabel = (JLabel) ((JLabel) e.getSource()).getParent().getComponents()[1];
								message = overlapedCalendarDayLabel.getText()+"-"+selectedMonth+"-"+selectedYear;
								//System.out.println(message);
							}
						}
						@Override
						public void mousePressed(MouseEvent e) {
							float x = e.getX();
							float y = e.getY();
							float yLimit = -((float)calendarDayHeight/(float)calendarDayWidth)*(x-(float)calendarDayWidth);
							if(lastPressed != null)
								lastPressed.setIcon(lastIcon);
							JLabel multiBtn = (JLabel) ((JLabel) e.getSource()).getParent().getComponents()[2];
							if(y < yLimit) {
								up=true;
								lastPressed = multiBtn;
								lastIcon = (ImageIcon) multiBtn.getIcon();
								multiBtn.setIcon(calendarUpDaySelectedIco);
							}
							else {
								up=false;
								lastPressed = multiBtn;
								lastIcon = (ImageIcon) multiBtn.getIcon();
								multiBtn.setIcon(calendarDownDaySelectedIco);
							}
						}
					});
				}
				count++;
			}
		}
	}
	
	private void fillCalendar(){
		count=0;
		int overlapedDayCount=overlapedDays-1;

		JLabel otherDay;
		JLabel dayBtn;
		JLabel multiBtn;
		JLabel overlapedCalendarDayLabel;	
		JLabel calendarDayLabel;
		for(int row=0;row<5;row++) {
			for(int column=0;column<7;column++) {
				otherDay = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[4];
				dayBtn = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[3];
				multiBtn = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[2];
				overlapedCalendarDayLabel = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[1];
				calendarDayLabel = (JLabel) ((JPanel) calendarDaysPane.getComponents()[count]).getComponents()[0];
				dayPane = (JPanel) calendarDaysPane.getComponents()[count];
				
				//
				dayBtn.setVisible(true);
				calendarDayLabel.setVisible(true);
				overlapedCalendarDayLabel.setVisible(false);
				multiBtn.setVisible(false);
				otherDay.setVisible(false);
				
				//To define if write the actual month days or last/next month days
				if(row==0 && column == startingWeekDay-1) startToWriteDay = true; 
				else if (dayCount > numberOfDays) startToWriteDay = false;
				
				//Reseting the overlaped calendar day label
				if(row==4) overlapedCalendarDayLabel.setText("");
					
				if(startToWriteDay) {
					if(overlapedDays > 0 && row == 4 && column < overlapedDays) {
						//Switch to see multibtn
						dayBtn.setVisible(false);
						multiBtn.setVisible(true);
						calendarDayLabel.setVisible(true);
						overlapedCalendarDayLabel.setVisible(true);
						otherDay.setVisible(false);
						Color dayColor;
						if(isToday(numberOfDays-overlapedDayCount))
							dayColor = Color.RED;
						else
							dayColor = Color.BLACK;
						overlapedCalendarDayLabel.setForeground(dayColor);
						overlapedCalendarDayLabel.setText(Integer.toString(numberOfDays-overlapedDayCount));					
						
						//multiBtn.setIcon(calendarDoubleDayUnpressedIco);
						if(isToday(dayCount))
							dayColor = Color.RED;
						else
							dayColor = Color.BLACK;
						calendarDayLabel.setText(Integer.toString(dayCount));
						calendarDayLabel.setForeground(dayColor);
						overlapedDayCount--;
					}
					else {
						Color dayColor;
						dayBtn.setVisible(true);
						multiBtn.setVisible(false);
						calendarDayLabel.setVisible(true);
						overlapedCalendarDayLabel.setVisible(false);
						otherDay.setVisible(false);
						dayBtn.setIcon(calendarDayUnpressedIco);
						calendarDayLabel.setText(Integer.toString(dayCount));
						if(isToday(dayCount))
							dayColor = Color.RED;
						else
							dayColor = Color.BLACK;
						calendarDayLabel.setForeground(dayColor);
					}
					dayCount++;
				}
				else {
					otherDay.setVisible(true);
					dayBtn.setVisible(false);
					multiBtn.setVisible(false);
					calendarDayLabel.setVisible(true);
					overlapedCalendarDayLabel.setVisible(false);
					otherDay.setIcon(calendarDayOtherIco);
					calendarDayLabel.setText(Integer.toString(otherMonthDays[otherMonthDayCount]));
					calendarDayLabel.setForeground(new Color(0,0,0,120));
					otherMonthDayCount++;
				}	
				count++;			
			}
		}
	}
	private boolean isToday(int day) {
		boolean today;
		today = selectedYear.equals(actualYear) && selectedMonth == actualMonth && day == actualDay;
		return today;
	}
	private void updatePreviousMonthBlocking() {
		blockPreviousMonths = selectedYear.equals(actualYear) && selectedMonth == actualMonth;
		/*System.out.println("SelYear: "+selectedYear+", actualYear: "+actualYear
				+", selMonth: "+selectedMonth+", actualMonth: "+actualMonth+", Years: "+
				selectedYear.equals(actualYear)+", Months: "+(selectedMonth == actualMonth));*/
	}
	int weekDay (int dia, int mes, int ano)
    {
        TimeZone timezone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timezone);
        calendar.set(ano, mes-1, dia);
        int nD=calendar.get(Calendar.DAY_OF_WEEK);
     
    
        return nD;
    }
	public static int daysInAMonth(int mes, int ano){
		mes--;
        switch(mes){
            case 0:  // Enero
            case 2:  // Marzo
            case 4:  // Mayo
            case 6:  // Julio
            case 7:  // Agosto
            case 9:  // Octubre
            case 11: // Diciembre
                return 31;
            case 3:  // Abril
            case 5:  // Junio
            case 8:  // Septiembre
            case 10: // Noviembre
                return 30;
            case 1:  // Febrero
                if ( ((ano%100 == 0) && (ano%400 == 0)) ||
                        ((ano%100 != 0) && (ano%  4 == 0))   )
                    return 29;  // Ao Bisiesto
                else
                    return 28;
            default:
                throw new java.lang.IllegalArgumentException(
                "El mes debe estar entre 0 y 11");
        }
}

}
