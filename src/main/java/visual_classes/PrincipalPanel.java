package visual_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import useful_classes.FileHandler;
import useful_classes.osChange;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;

public class PrincipalPanel extends JPanel {
	JButton nuevoBtn = new JButton();
	JButton detenerEjecucionBtn = new JButton();
	JButton eliminarBtn = new JButton();
	JButton cambiarContrasenaBtn = new JButton();
	JButton playPrevBtn = new JButton();
	JPanel container = new JPanel();
	JLabel base = new JLabel();
	
	JPanel execution;
	JPanel scrollLabels = new JPanel();
	JPanel scrollLabelsContainer = new JPanel();
	JLabel scrollBar = new JLabel();
	JLabel tabs = new JLabel();
	JLabel selection = new JLabel();
	
	ImageIcon baseIco = new ImageIcon(this.getClass().getResource("/principal_base.png"));
	ImageIcon tabsTodo = new ImageIcon(this.getClass().getResource("/principal_todo.png"));
	ImageIcon tabsToques = new ImageIcon(this.getClass().getResource("/principal_toques.png"));
	ImageIcon tabsMelodias = new ImageIcon(this.getClass().getResource("/principal_melodias.png"));
	ImageIcon tabsSecuencias = new ImageIcon(this.getClass().getResource("/principal_secuencias.png"));
	ImageIcon selectionIco = new ImageIcon(this.getClass().getResource("/element_select.png"));
	ImageIcon scrollBarIco = new ImageIcon(this.getClass().getResource("/scroll_bar.png"));
	ImageIcon nuevoBtnIcoUnpressed = new ImageIcon(this.getClass().getResource("/nuevo_btn_unpressed.png"));
	ImageIcon nuevoBtnIcoPressed = new ImageIcon(this.getClass().getResource("/nuevo_btn_pressed.png"));
	ImageIcon detenerEjecucionBtnIcoUnpressed = new ImageIcon(this.getClass().getResource("/detener_ejecucion_btn_unpressed.png"));
	ImageIcon detenerEjecucionBtnIcoPressed = new ImageIcon(this.getClass().getResource("/detener_ejecucion_btn_pressed.png"));
	ImageIcon eliminarBtnIcoUnpressed = new ImageIcon(this.getClass().getResource("/eliminar_btn_unpressed.png"));
	ImageIcon eliminarBtnIcoPressed = new ImageIcon(this.getClass().getResource("/eliminar_btn_pressed.png"));	
	ImageIcon cambiarContrasenaIcoUnpressed = new ImageIcon(this.getClass().getResource("/cambiar_contrasena_btn_unpressed.png"));
	ImageIcon cambiarContrasenaIcoPressed = new ImageIcon(this.getClass().getResource("/cambiar_contrasena_btn_pressed.png"));
	ImageIcon playPrevIco = new ImageIcon(this.getClass().getResource("/play_prev.png"));
	ImageIcon notPlayPrevIco = new ImageIcon(this.getClass().getResource("/not_play_prev.png"));
	ImageIcon pressedPlayPrevIco = new ImageIcon(this.getClass().getResource("/pressed_play_prev.png"));
	ImageIcon pressedNotPlayPrevIco = new ImageIcon(this.getClass().getResource("/pressed_not_play_prev.png"));
	int scale = 2;
	boolean executionPressed = false;
	boolean scrollBarPressed = false;
	int scrollMoveY = 0;
	//Buttons
	int buttonsGap;
	int buttonsX;
	int buttonsWidth;
	int buttonsHeight;
	int nuevoBtnY;
	int detenerEjecucionBtnY;
	int eliminarBtnY;
	int cambiarContrasenaY;
	//Container
	int containerX;
	int containerY;
	int containerWidth;
	int containerHeight;
	//Base
	int baseX;
	int baseY;
	int baseWidth;
	int baseHeight;
	//ScrollLabelsContainer
	int scrollLabelsContainerGap;
	int scrollLabelsContainerX;
	int scrollLabelsContainerY;
	int scrollLabelsContainerWidth;
	int scrollLabelsContainerHeight;
	//ScrollLabels
	int scrollLabelsX;
	int scrollLabelsY;
	int scrollLabelsWidth;
	int scrollLabelsHeight;
	//execution
	int executionGap;
	int executionX;
	int executionY;
	int executionWidth;
	int executionHeight;
	// ScrollBar
	int scrollBarX;
	int scrollBarY;
	int scrollBarWidth;
	int scrollBarHeight;
	int scrollBarMaxHeight;
	int scrollBarMinHeight;
	//Tabs
	int tabsX;
	int tabsY;
	int tabsWidth;
	int tabsHeight;
	//Selection
	int selectionX;
	int selectionY;
	int selectionWidth;
	int selectionHeight;
	//IndividualLabel
	int individualLabelX;
	int individualLabelY;
	int individualLabelWidth;
	int individualLabelHeight;
	//PlayPrevBtn
	int playPrevX;
	int playPrevY;
	int playPrevWidth;
	int playPrevHeight;
	
	public boolean playPrev = true;
	
	String mes[] = {"enero", "febrero", "marzo", "abril", 
			"mayo", "junio", "julio", "agosto", "septiembre", 
			"octubre", "noviembre", "diciembre"};
	String selectedData="";
	JLabel lastSelectedBack;
	int tabNumber;
	ArrayList<String> todoNames = new ArrayList<String>();
	ArrayList<String> toquesNames = new ArrayList<String>();
	ArrayList<String> melodiasNames = new ArrayList<String>();
	ArrayList<String> secuenciasNames = new ArrayList<String>();
	FileHandler executions = new FileHandler();
	FileHandler config = new FileHandler();
	osChange os = new osChange();
	Dialog dialog = new Dialog();
	MainPane main;
	int screenWidth;
	int screenHeight;
	MouseAdapter eliminarBtnEvent;
	/**
	 * Create the panel.
	 */
	public PrincipalPanel() {
		Dimension screenSize = os.setDimension();
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
		//System.out.println("Test is: "+getClass().getResource("/config.int"));
		config.setFilename("config.int");
		playPrev = Boolean.valueOf(config.readFileLine()[0]); 
		
		//Panel
		int panelWidth = screenWidth;
		int panelHeight = screenHeight;
		int panelX=0;
		int panelY=0;
		//Setting this panel
		setOpaque(false);
		setBounds(panelX, panelY, panelWidth, panelHeight);
		setLayout(null);
		add(dialog);
		
		//Container
		containerX = 0;
		containerY = 0;
		containerWidth = (int)Math.round(baseIco.getIconWidth()/scale);
		containerHeight = screenHeight;
		container.setLayout(null);
		container.setBorder(null);
		container.setOpaque(false);
		container.setBounds(containerX,containerY,containerWidth,containerHeight);
		
		//Tabs
		tabsX = 0;
		tabsY = 0;
		tabsWidth = (int)Math.round(tabsTodo.getIconWidth()/scale);
		tabsHeight= (int)Math.round(tabsTodo.getIconHeight()/scale);
		tabs.setBounds(tabsX,tabsY,tabsWidth,tabsHeight);
		
		//Base
		baseX = 0;
		baseY = tabsHeight;
		baseWidth = (int)Math.round(baseIco.getIconWidth()/scale);
		baseHeight = (int)Math.round(baseIco.getIconHeight()/scale);
		base.setBounds(baseX,baseY,baseWidth,baseHeight);
		
		//ScrollLabelsContainer
		scrollLabelsContainerGap = 10;
		scrollLabelsContainerX = scrollLabelsContainerGap;
		scrollLabelsContainerY = baseY+scrollLabelsContainerGap;
		scrollLabelsContainerWidth = (int) Math.round(baseWidth - scrollLabelsContainerGap * 2);
		scrollLabelsContainerHeight = (int) Math.round(baseHeight - scrollLabelsContainerGap * 2);
		scrollLabelsContainer.setLayout(null);
		scrollLabelsContainer.setOpaque(false);
		scrollLabelsContainer.setBounds(scrollLabelsContainerX,scrollLabelsContainerY,scrollLabelsContainerWidth,scrollLabelsContainerHeight);
		//scrollLabelsContainer.setOpaque(true);
		
		//ScrollLabels
		scrollLabelsX = 0;
		scrollLabelsY = 0;
		scrollLabelsWidth = scrollLabelsContainerWidth;
		scrollLabelsHeight = scrollLabelsContainerHeight;
		scrollLabels.setLayout(null);
		scrollLabels.setOpaque(false);
		scrollLabels.setBounds(scrollLabelsX,scrollLabelsY,scrollLabelsWidth,scrollLabelsHeight);
		
		//Execution
		executionGap = 10;
		executionWidth=scrollLabelsWidth;
		executionHeight=60;
		executionX=0;
		
		//Buttons
		buttonsGap = 20;
		float overScale = 1.05f;
		buttonsWidth = (int)Math.round(nuevoBtnIcoUnpressed.getIconWidth()/(scale*overScale));
		buttonsHeight = (int)Math.round(nuevoBtnIcoUnpressed.getIconHeight()/(scale*overScale));
		buttonsX = (containerX + containerWidth)+((screenWidth-(containerX + containerWidth))/2)-buttonsWidth/2;
		//buttonsX=500;
		nuevoBtnY = 100;
		detenerEjecucionBtnY = nuevoBtnY+buttonsHeight+buttonsGap;
		eliminarBtnY = detenerEjecucionBtnY+buttonsHeight+buttonsGap;
		cambiarContrasenaY = eliminarBtnY+buttonsHeight+buttonsGap;
		
		placeBtns(false);
		
		//selection
		selectionX=0;
		selectionY=0;
		selectionWidth = (int)Math.round(selectionIco.getIconWidth()/scale);
		selectionHeight = (int)Math.round(selectionIco.getIconHeight()/scale);
		
		//ScrollBar
		scrollBarWidth = (int)Math.round(scrollBarIco.getIconWidth()/scale);
		scrollBarHeight = (int)Math.round(scrollBarIco.getIconHeight()/scale);
	//	scrollBarX = ;
	//	scrollBarY;
		//scrollBarMaxHeight;
		//scrollBarMinHeight;
		
		//Icon		
		baseIco = new ImageIcon(baseIco.getImage().getScaledInstance(baseWidth, baseHeight,java.awt.Image.SCALE_SMOOTH));
		tabsTodo = new ImageIcon(tabsTodo.getImage().getScaledInstance(tabsWidth, tabsHeight,java.awt.Image.SCALE_SMOOTH));
		tabsToques = new ImageIcon(tabsToques.getImage().getScaledInstance(tabsWidth, tabsHeight,java.awt.Image.SCALE_SMOOTH));
		tabsMelodias = new ImageIcon(tabsMelodias.getImage().getScaledInstance(tabsWidth, tabsHeight,java.awt.Image.SCALE_SMOOTH));
		tabsSecuencias = new ImageIcon(tabsSecuencias.getImage().getScaledInstance(tabsWidth, tabsHeight,java.awt.Image.SCALE_SMOOTH));
		selectionIco = new ImageIcon(selectionIco.getImage().getScaledInstance(selectionWidth, selectionHeight,java.awt.Image.SCALE_SMOOTH));
		scrollBarIco = new ImageIcon(scrollBarIco.getImage().getScaledInstance(baseWidth, baseHeight,java.awt.Image.SCALE_SMOOTH));
		nuevoBtnIcoUnpressed = new ImageIcon(nuevoBtnIcoUnpressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		nuevoBtnIcoPressed = new ImageIcon(nuevoBtnIcoPressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		detenerEjecucionBtnIcoUnpressed = new ImageIcon(detenerEjecucionBtnIcoUnpressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		detenerEjecucionBtnIcoPressed = new ImageIcon(detenerEjecucionBtnIcoPressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		eliminarBtnIcoUnpressed = new ImageIcon(eliminarBtnIcoUnpressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		eliminarBtnIcoPressed = new ImageIcon(eliminarBtnIcoPressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		cambiarContrasenaIcoUnpressed = new ImageIcon(cambiarContrasenaIcoUnpressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		cambiarContrasenaIcoPressed = new ImageIcon(cambiarContrasenaIcoPressed.getImage().getScaledInstance(buttonsWidth, buttonsHeight,java.awt.Image.SCALE_SMOOTH));
		
		//Icon set
		tabSelect(0);
		scrollBar.setIcon(scrollBarIco);
		base.setIcon(baseIco);
		nuevoBtn.setIcon(nuevoBtnIcoUnpressed);
		nuevoBtn.setPressedIcon(nuevoBtnIcoPressed);
		detenerEjecucionBtn.setIcon(detenerEjecucionBtnIcoUnpressed);
		detenerEjecucionBtn.setPressedIcon(detenerEjecucionBtnIcoPressed);
		eliminarBtn.setIcon(eliminarBtnIcoUnpressed);
		eliminarBtn.setPressedIcon(eliminarBtnIcoPressed);
		cambiarContrasenaBtn.setIcon(cambiarContrasenaIcoUnpressed);
		cambiarContrasenaBtn.setPressedIcon(cambiarContrasenaIcoPressed);
		
		//nuevo
		nuevoBtn.setBorder(null);
		nuevoBtn.setContentAreaFilled(false);
		nuevoBtn.setBounds(buttonsX, nuevoBtnY, buttonsWidth, buttonsHeight);
		add(nuevoBtn);
		
		//editar
		detenerEjecucionBtn.setBorder(null);
		detenerEjecucionBtn.setContentAreaFilled(false);
		detenerEjecucionBtn.setBounds(buttonsX, detenerEjecucionBtnY, buttonsWidth, buttonsHeight);
		add(detenerEjecucionBtn);
		
		//eliminar
		eliminarBtn.setBorder(null);
		eliminarBtn.setContentAreaFilled(false);
		eliminarBtn.setBounds(buttonsX, eliminarBtnY, buttonsWidth, buttonsHeight);
		add(eliminarBtn);
		
		//contraseña
		cambiarContrasenaBtn.setBorder(null);
		cambiarContrasenaBtn.setContentAreaFilled(false);
		cambiarContrasenaBtn.setBounds(buttonsX, cambiarContrasenaY, buttonsWidth, buttonsHeight);
		add(cambiarContrasenaBtn);
						
		add(container);
		container.add(tabs);
		container.add(scrollLabelsContainer);
		container.add(base);
		scrollLabelsContainer.add(scrollBar);
		scrollLabelsContainer.add(scrollLabels);
	}
	
	public void setMainPane(MainPane main) {
		this.main = main;
		System.out.println("Set main pane");
		fillNameList();
		setActions();
		setPlayPrevBtn();
	}
	
	public void setPlayPrevBtn() {
		playPrevX = screenWidth - (this.main.squareButtonHorizontalGap + this.main.squareButtonSize)*2;
		playPrevY = this.main.squareButtonVerticalGap;
		playPrevWidth = this.main.squareButtonSize;
		playPrevHeight = this.main.squareButtonSize;
		playPrevIco = new ImageIcon(playPrevIco.getImage().getScaledInstance( this.main.squareButtonSize, this.main.squareButtonSize,java.awt.Image.SCALE_SMOOTH));
		notPlayPrevIco = new ImageIcon(notPlayPrevIco.getImage().getScaledInstance( this.main.squareButtonSize, this.main.squareButtonSize,java.awt.Image.SCALE_SMOOTH));
		pressedPlayPrevIco = new ImageIcon(pressedPlayPrevIco.getImage().getScaledInstance( this.main.squareButtonSize, this.main.squareButtonSize,java.awt.Image.SCALE_SMOOTH));
		pressedNotPlayPrevIco = new ImageIcon(pressedNotPlayPrevIco.getImage().getScaledInstance( this.main.squareButtonSize, this.main.squareButtonSize,java.awt.Image.SCALE_SMOOTH));
		
		playPrevBtn.setBounds(playPrevX,playPrevY,playPrevWidth,playPrevHeight);
		if(playPrev) {
			playPrevBtn.setIcon(playPrevIco);
			playPrevBtn.setPressedIcon(pressedPlayPrevIco);
		} else {
			playPrevBtn.setIcon(notPlayPrevIco);
			playPrevBtn.setPressedIcon(pressedNotPlayPrevIco);
		}
		playPrevBtn.setBorder(null);
		playPrevBtn.setContentAreaFilled(false);
		add(playPrevBtn);
	}
	
	
	public void placeBtns(boolean fourthBtnVisible) {
		nuevoBtnY = fourthBtnVisible? 100:150; //100
		eliminarBtnY = nuevoBtnY+buttonsHeight+buttonsGap;
		cambiarContrasenaY = eliminarBtnY+buttonsHeight+buttonsGap;
		detenerEjecucionBtnY = cambiarContrasenaY+buttonsHeight+buttonsGap;
		detenerEjecucionBtn.setVisible(fourthBtnVisible);
		
		nuevoBtn.setLocation(nuevoBtn.getLocation().x,nuevoBtnY);
		eliminarBtn.setLocation(eliminarBtn.getLocation().x,eliminarBtnY);
		cambiarContrasenaBtn.setLocation(cambiarContrasenaBtn.getLocation().x,cambiarContrasenaY);
		detenerEjecucionBtn.setLocation(detenerEjecucionBtn.getLocation().x,detenerEjecucionBtnY);
		
	}
	
	public void reset(boolean resetTabNumber) {	
		tabNumber = resetTabNumber? 0:tabNumber;
		clearList();
		switch(tabNumber) {
		case 0:
			showExecutionList(todoNames);
			break;
		case 1:
			showExecutionList(toquesNames);
			break;
		case 2:
			showExecutionList(melodiasNames);
			break;
		case 3:
			showExecutionList(secuenciasNames);
			break;
		}
		if(lastSelectedBack!=null) lastSelectedBack.setVisible(false);
		tabSelect(tabNumber);
	}
	
	public void fillNameList() {
		String[] lines;
		boolean fileEmpty;
		toquesNames.clear();
		melodiasNames.clear();
		secuenciasNames.clear();
		todoNames.clear();

		//Toques
		executions.setDirection("sav");
		executions.setFilename("toques.int");
		lines = executions.readFileLine();
		fileEmpty = lines.length == 1 && lines[0].equals(""); 
		
		if(!fileEmpty)
			for(String line: lines) {
				toquesNames.add(line);
			}
		
		//Melodas
		executions.setFilename("melodias.int");
		lines = executions.readFileLine();
		fileEmpty = lines.length == 1 && lines[0].equals(""); 
		
		if(!fileEmpty)
			for(String line: lines) {
				melodiasNames.add(line);
			}
		
		//Secuencias
		executions.setFilename("secuencias.int");
		lines = executions.readFileLine();
		fileEmpty = lines.length == 1 && lines[0].equals(""); 
		
		if(!fileEmpty)
			for(String line: lines) {
				secuenciasNames.add(line);
			}
		
		todoNames.addAll(toquesNames);
		todoNames.addAll(melodiasNames);
		todoNames.addAll(secuenciasNames);
		main.scheduledExecutionList = this.todoNames;
		Collections.sort(todoNames);
	}
	
	private void clearList() {
		scrollLabels.removeAll();
		scrollLabelsY=0;
		scrollLabels.setBounds(scrollLabelsX,scrollLabelsY,scrollLabelsWidth,scrollLabelsHeight);
	}
	
	public void showExecutionList(ArrayList<String> nameList) {
		scrollLabelsHeight = (executionHeight+executionGap)*nameList.size()-executionGap;
		scrollLabels.setBounds(scrollLabelsX, scrollLabelsY, scrollLabelsWidth, scrollLabelsHeight);
		for (int i = 0; i < nameList.size(); i++) {
			executionSetting(i,nameList.get(i));
			executionActionListener();
		}
		scrollBarSetting();
	}
	
	private void executionSetting(int iteration,String text) {
		//execution
		JLabel originalText = new JLabel();
		JLabel nameUp = new JLabel();
		JLabel nameDown = new JLabel();
		JLabel back = new JLabel();
		JLabel selectedBack = new JLabel();
		execution = new JPanel();
		execution.setLayout(null);
		execution.setOpaque(false);
		executionY=(executionHeight+executionGap)*iteration;
		execution.setBackground(Color.BLUE);
		execution.setBounds(executionX,executionY,executionWidth,executionHeight);
		Font font = new Font("Quicksand Medium", Font.BOLD, (int)Math.round((executionHeight - 10)*1f/2f));
		
		originalText.setText(text);
		originalText.setVisible(false);
		execution.add(originalText);
		
		String[] parts = text.split(";",3);
		if(parts[0].contains("-")) {
			String[] date = parts[0].split("-");
			parts[0] = date[0] + " de " + mes[Integer.parseInt(date[1])-1] + " de " + date[2];
		}
		else {
			text="";
			for(int i=0;i<parts[0].length();i++) {
				text += dayWeekNumberToName(parts[0].charAt(i));
				if(i<parts[0].length()-1)
					text+=", ";
			}
			parts[0] = text;
		}
		
		String outText = parts[0]+" "+parts[1];
		
		selectedBack.setBackground(new Color(0,146,250));
		selectedBack.setVisible(false);
		selectedBack.setLocation(0,0);
		selectedBack.setSize(execution.getSize());
		selectedBack.setOpaque(true);
		
		back.setBackground(new Color(0,0,0,10));
		back.setLocation(0,0);
		back.setSize(execution.getSize());
		back.setOpaque(true);
		
		nameUp.setFont(font);
		nameUp.setHorizontalAlignment(SwingConstants.LEFT);
		nameUp.setVerticalAlignment(SwingConstants.CENTER);
		nameUp.setForeground(new Color(0,42,53));
		nameUp.setSize(execution.getWidth(),(int)Math.round(execution.getHeight()*1f/2f));
		nameUp.setLocation(0,0);
		nameUp.setText(outText);
		
		nameDown.setFont(font);
		nameDown.setHorizontalAlignment(SwingConstants.LEFT);
		nameDown.setVerticalAlignment(SwingConstants.CENTER);
		nameDown.setForeground(nameUp.getForeground());
		nameDown.setSize(nameUp.getSize());
		nameDown.setLocation(0,nameDown.getHeight());	
		nameDown.setText(parts[2].split("\\.")[0]);
		
		execution.add(nameUp); 		//1
		execution.add(nameDown);	//2
		execution.add(selectedBack);//3
		execution.add(back);		//4
		scrollLabels.add(execution);
	}
	
	private void executionActionListener() {
		execution.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				executionPressed = true;
				scrollMoveY = MouseInfo.getPointerInfo().getLocation().y;
				//System.out.println("Touch pressed: "+ scrollMoveY);
				//System.out.println(((JLabel) ((JPanel) e.getSource()).getComponents()[0]).getText());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				executionPressed = false;
				// System.out.println("Touch released: "+MouseInfo.getPointerInfo().getLocation().y);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				//JLabel back = ((JLabel) ((JPanel) e.getSource()).getComponents()[2]);
				selectedData = ((JLabel) ((JPanel) e.getSource()).getComponents()[0]).getText();
				JLabel selectedBack = ((JLabel) ((JPanel) e.getSource()).getComponents()[3]);
				if(lastSelectedBack != null)
					lastSelectedBack.setVisible(false);
				
				selectedBack.setVisible(true);
				lastSelectedBack = selectedBack;
				//System.out.println("Selected data: "+selectedData);
			}
		});
		execution.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (executionPressed) {
					//System.out.println("Touch dragged: "+MouseInfo.getPointerInfo().getLocation().y);
					scrollLabelsY = MouseInfo.getPointerInfo().getLocation().y - scrollMoveY + scrollLabels.getY();
					
					scrollMoveY = MouseInfo.getPointerInfo().getLocation().y;
					selection.setVisible(false);
					int limit = scrollLabelsContainerHeight - scrollLabelsHeight;
					scrollLabelsY = Math.max(scrollLabelsY, limit);
					scrollLabelsY = Math.min(scrollLabelsY, 0);
					
					scrollLabels.setBounds(scrollLabelsX, scrollLabelsY, scrollLabelsWidth, scrollLabelsHeight);

					int scrollBarYMin = 0;
					int scrollBarYMax = scrollLabelsContainerHeight - scrollBarHeight;
					int scrollLabelsYMin = 0;
					int scrollLabelsYMax = scrollLabelsContainerHeight - scrollLabelsHeight;
					scrollBarY = (int) Math
							.round((scrollLabelsY - scrollLabelsYMin) * 1f / (scrollLabelsYMax - scrollLabelsYMin)
									* (scrollBarYMax - scrollBarYMin) + scrollBarYMin);
					scrollBar.setBounds(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);
				}
			}
		});
	}
	
	private String dayWeekNumberToName (char c)
    {     
		String letraD = ""; 
        switch (c){
            case '1': letraD = "Dom";
                break;
            case '2': letraD = "Lun";
                break;
            case '3': letraD = "Mar";
                break;
            case '4': letraD = "Mie";
                break;
            case '5': letraD = "Jue";
                break;
            case '6': letraD = "Vie";
                break;
            case '7': letraD = "Sab";
                break;
        }
        return letraD;
    }
	
	private void scrollBarSetting() {
		// ScrollBar
		scrollBarMaxHeight = scrollLabelsContainerHeight;
		scrollBarMinHeight = (int) Math.round(scrollLabelsContainerHeight * 0.2);
		scrollBarWidth = 50;
		scrollBarHeight = scrollBarMaxHeight - (int)Math.round((scrollLabelsHeight - scrollLabelsContainerHeight)*0.5);
		scrollBarHeight = Math.max(scrollBarHeight, scrollBarMinHeight);
		scrollBarX = scrollLabelsContainerWidth - scrollBarWidth;
		scrollBarY = 0;
		scrollBarIco = new ImageIcon(scrollBarIco.getImage().getScaledInstance(scrollBarWidth, scrollBarHeight, java.awt.Image.SCALE_SMOOTH));
		scrollBar.setIcon(scrollBarIco);
		scrollBar.setBounds(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);
		// If it appears
		if (scrollLabelsHeight <= scrollLabelsContainerHeight)
			scrollBar.setVisible(false);
		else
			scrollBar.setVisible(true);

		scrollBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				scrollBarPressed = true;
				scrollMoveY = MouseInfo.getPointerInfo().getLocation().y;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				scrollBarPressed = false;
			}
		});
		scrollBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (scrollBarPressed) {
					scrollBarY = MouseInfo.getPointerInfo().getLocation().y - scrollMoveY + scrollBar.getY();
					scrollMoveY = MouseInfo.getPointerInfo().getLocation().y;
					selection.setVisible(false);
					int limit = scrollLabelsContainerHeight - scrollBarHeight;
					scrollBarY = Math.min(scrollBarY, limit);
					scrollBarY = Math.max(scrollBarY, 0);
					scrollBar.setBounds(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight);

					int scrollBarYMin = 0;
					int scrollBarYMax = limit;
					int scrollButtonsYMin = 0;
					int scrollButtonsYMax = scrollLabelsContainerHeight - scrollLabelsHeight;
					scrollLabelsY = (int) Math
							.round((scrollBarY - scrollBarYMin) * 1f / (scrollBarYMax - scrollBarYMin)
									* (scrollButtonsYMax - scrollButtonsYMin) + scrollButtonsYMin);
					scrollLabels.setBounds(scrollLabelsX, scrollLabelsY, scrollLabelsWidth, scrollLabelsHeight);
				}
			}
		});
	}
	
	private void tabSelect(int tabNumber){
		switch(tabNumber) {
		case 0:
			tabs.setIcon(tabsTodo);
			break;
		case 1:
			tabs.setIcon(tabsToques);
			break;
		case 2:
			tabs.setIcon(tabsMelodias);
			break;
		case 3:
			tabs.setIcon(tabsSecuencias);
			break;
		}
	}
	
	private void setActions() {
		playPrevBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playPrev = !playPrev;
				config.writeFile(Boolean.toString(playPrev),false);
				if(playPrev) {
					playPrevBtn.setIcon(playPrevIco);
					playPrevBtn.setPressedIcon(pressedPlayPrevIco);
				} else {
					playPrevBtn.setIcon(notPlayPrevIco);
					playPrevBtn.setPressedIcon(pressedNotPlayPrevIco);
				}
			}
		});
		
		nuevoBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				reset(true);
				main.atribute.setup = false;
				main.menuNavegation.next(main.atribute);
				dialog.setVisible(false);
				selectedData="";
			}
		});
		
		detenerEjecucionBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				main.sendExecution.stopBellExecution();
				main.sendExecution.stopSong();
			}
		});
		
		eliminarBtnEvent = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				//System.out.println("selectedData: "+selectedData+" "+!selectedData.equals(""));
				if(!selectedData.equals("")) {
					executions.setDirection("sav");
					String[] fileLines;
					
					String extension = selectedData.split(";")[2].split("\\.")[1];
					//System.out.println("extension: "+extension);
					switch(extension) {
					case "mp3":
						executions.setFilename("melodias.int");
						break;
					case "toc":
						executions.setFilename("toques.int");
						break;
					case "sec":
						executions.setFilename("secuencias.int");
						break;
					}
									
					fileLines = executions.readFileLine();
					executions.writeFile("",false);
					for(String line: fileLines) {
						if(!line.equals(selectedData)) {
							executions.writeFileln(line,true);
						}
					}
					selectedData="";
					fillNameList();
					reset(false);
					repaint();
					
				}
				dialog.setVisible(false);
			}
		};
		dialog.setSiBtnAccionListener(eliminarBtnEvent);
		
		eliminarBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				if(!selectedData.equals(""))
				dialog.executionEliminationMessage(screenWidth,screenHeight);
			}
		});
		
		cambiarContrasenaBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				reset(true);
				main.atribute.setup = true;
				main.menuNavegation.next(main.atribute);
				main.virtualKeyboard.setHeight(290);
				dialog.setVisible(false);
				selectedData="";
			}
		});
		
		tabs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.dateAndHour.update();
				int x = e.getX();
				//System.out.println("x: "+x);
				if(x<=90) {
					if(tabNumber!=0) {
						tabNumber = 0;
						clearList();
						showExecutionList(todoNames);
						//if(lastSelectedBack != null)
						//	lastSelectedBack.setVisible(false);
					}
				}
				else if(x<=234) {
					if(tabNumber!=1) {
						tabNumber = 1;
						clearList();
						//lastSelectedBack.setVisible(false);
						showExecutionList(toquesNames);
					}
				}
				else if(x<=407) {
					if(tabNumber!=2) {
						tabNumber = 2;
						clearList();
						showExecutionList(melodiasNames);
					}
				}
				else {
					if(tabNumber!=3) {
						tabNumber = 3;
						clearList();
						showExecutionList(secuenciasNames);
					}
				}
				tabSelect(tabNumber);
				repaint();
			}
		});
	}
}
