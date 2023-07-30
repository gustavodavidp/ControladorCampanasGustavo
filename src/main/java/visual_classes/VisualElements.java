package visual_classes;

import javax.swing.JButton;

public class VisualElements {
	public class CustomButton extends JButton {
		private String name;
		
		public CustomButton(String name){
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
