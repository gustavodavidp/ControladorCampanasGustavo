package useful_classes;

public class MenuItemTree {
	private int level;
	private String itemName;
	private MenuAtributes atribute;
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void nextLevel(){
		level++;
		if(level == 7)
			level = 1;
	}
	
	public void previousLevel() {
		level--;
	}
	
	public String inLevelItemSelection(MenuAtributes atribute) {
		this.atribute = atribute;
		switch(level) {
		case 0:
			levelZero();
			break;
		case 1:
			levelOne();
			break;
		case 2:
			levelTwo();
			break;
		case 3:
			levelThree();
			break;
		case 4:
			levelFour();
			break;
		case 5:
			levelFive();
			break;
		case 6:
			levelSix();
			break;
		}
		return itemName;
	}
	
	private void levelZero() {
		if(atribute.first) 
			itemName = "FirstTimePanel";
		else
			itemName = "StartSessionPanel";
	}
	
	private void levelOne() {
		itemName = "PrincipalPanel";
	}
	
	private void levelTwo() {
		if(atribute.setup)
			itemName = "ChangePasswordPanel";
		else
			itemName = "ExecutionTypePanel";
	}
	
	private void levelThree() {
		itemName = "ExecutionTimePanel";
	}

	private void levelFour() {
		switch(atribute.time) {
		case INMEDIATAS:
			itemName = "SelectExecutionPanel";
			break;
		case PROGRAMADAS:
			itemName = "SelectDatePanel";
			break;
		case REPETITIVAS:
			itemName = "SelectDayHourPanel";
			break;
		}
	}
	
	private void levelFive() {
		switch(atribute.time) {
		case PROGRAMADAS:
			itemName = "SelectHourPanel";
			break;
		case REPETITIVAS:
			itemName = "SelectExecutionPanel";
			break;
		}
	}
	
	private void levelSix() {
		itemName = "SelectExecutionPanel";
	}



}
