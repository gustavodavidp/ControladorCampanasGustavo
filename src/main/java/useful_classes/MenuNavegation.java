package useful_classes;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MenuNavegation {
	private MenuItemTree menu = new MenuItemTree();
	private String itemName = "ExecutionTimePanel";
	private ArrayList<String> hideExceptions = new ArrayList<String>(); 
	private Component[] components;
	private ArrayList<String> visibleComponents;
	
	public MenuNavegation(JPanel container){
		components = container.getComponents();
		menu.setLevel(-1);
		hideExceptions.add("back");
		hideExceptions.add("lock");
		hideExceptions.add("DateAndHour");
		
	}
	
	public void next(MenuAtributes atribute) {
		menu.nextLevel();
		itemName = menu.inLevelItemSelection(atribute);
		panelSwitch();
	}
	
	public String getActualPane() {
		return itemName;
	}
	
	public void goBack(MenuAtributes atribute) {
		menu.previousLevel();
		itemName = menu.inLevelItemSelection(atribute);
		panelSwitch();
	}
	
	public void goToStart(MenuAtributes atribute) {
		menu.setLevel(0);
		itemName = menu.inLevelItemSelection(atribute);
		panelSwitch();
	}
	
	public void goToMain(MenuAtributes atribute) {
		menu.setLevel(1);
		itemName = menu.inLevelItemSelection(atribute);
		panelSwitch();
	}
	
	public void goTo(MenuAtributes atribute, int level) {
		menu.setLevel(level);
		itemName = menu.inLevelItemSelection(atribute);
		panelSwitch();
	}
	
	public void panelSwitch() {
		Component selectedComponent = null;
		panelExceptionHandler();
		hideAllComponents(true);
		selectedComponent = componentSearch(itemName);
		selectedComponent.setVisible(true);	
	}
	
	public void screenSaver() {
		boolean activated = componentSearch("ScreenSaver").isVisible();
		if(!activated) 
			visibleComponents = hideAllComponents(false);
		else
			for(String component: visibleComponents)
				componentSearch(component).setVisible(true);
		componentSearch("ScreenSaver").setVisible(!activated);
	}
	
	private Component componentSearch(String name) {
		Component selectedComponent = null;
		for (int i = 0; i < components.length; i++)
		{
			String componentName = getComponentName(components[i]);
			if(name.equals(componentName))
				selectedComponent = components[i];
		}
		return selectedComponent;
	}
	
	private void addIfListContains(String panel) {
		if(!hideExceptions.contains(panel))
			hideExceptions.add(panel);
	}
	
	private void removeIfListContains(String panel) {
		if(hideExceptions.contains(panel)) {
			hideExceptions.remove(panel);
		}
	}
	
	private void panelExceptionHandler() {
		if(itemName.equals("StartSessionPanel") || itemName.equals("FirstTimePanel")) {
			removeIfListContains("back");
			removeIfListContains("lock");
			addIfListContains("screen");
		}
		else if(itemName.equals("PrincipalPanel")) {
			removeIfListContains("back");
			removeIfListContains("screen");
			addIfListContains("lock");
		}
		else {
			removeIfListContains("screen");
			addIfListContains("back");
			addIfListContains("lock");
		}
	}
	
	private ArrayList<String> hideAllComponents(boolean includeExceptions) {
		ArrayList<String> visibleComponents = new ArrayList<String>();
		for (int i = 0; i < components.length; i++)
		{
			String componentName = getComponentName(components[i]);
			if(components[i].isVisible())
				visibleComponents.add(componentName);
			components[i].setVisible(false);
			
			//System.out.println("Exception list: ");
			if(includeExceptions) {	
					for(int j=0;j<hideExceptions.size();j++) {
						if(componentName.equals(hideExceptions.get(j)))
							components[i].setVisible(true);
						//System.out.println(hideExceptions.get(j));
					}
			}
		}
		return visibleComponents;
	}
	
	private String getComponentName(Component component) {
		String componentName = component.getClass().toString().split("\\.")[1];
		if(componentName.startsWith("VisualElements"))
			componentName = component.getName();
		return componentName;
	}

	

}
