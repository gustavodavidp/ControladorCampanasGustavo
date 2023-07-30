package useful_classes;

import javax.swing.JComponent;

public class Movement extends Thread {
	private float initialPosition;
	private float finalPosition;
	private float initialVelocity;
	private float aceleration;
	private float time = 0;
	private float halfTime; 
	private float duration;
	private JComponent objetiveElement;
	private float objetiveParameter;
	private String[] movementTypes = {"uniform","accelerated","slowedDown","custom"};
	private String selectedMovementType;
	private char positionComponent;
	private float actualPosition;
	private long timeInterval = 5;
	private int componentX;
	private int componentY;
	private int compensation = 300; //386
	private int smoothCompensation = 700;
	private int slowCompensation = 300;
	private float percentage = 0.60f;
	private boolean increase = false;
	private float difference;
	
	
		
	public Movement(JComponent objetiveElement) {
		this.objetiveElement = objetiveElement;
	}
	
	public void setUniformMovement(int x0, int x1, int duration,char positionComponent) {
		selectedMovementType = movementTypes[0];
		initialPosition = x0;
		finalPosition = x1;
		this.duration = duration - slowCompensation;
		this.positionComponent = positionComponent;
		initialVelocity = this.duration/(finalPosition - initialPosition);
		componentX = objetiveElement.getLocation().x;
		componentY = objetiveElement.getLocation().y;
	}
	
	public void setUniformMovement(int x1,int duration,char positionComponent) {
		selectedMovementType = movementTypes[0];
		this.duration = duration - compensation;
		this.positionComponent = positionComponent;
		componentX = objetiveElement.getLocation().x;
		componentY = objetiveElement.getLocation().y;
		if(positionComponent == 'x')
			initialPosition = componentX;
		else
			initialPosition = componentY;
		finalPosition = x1;
		initialVelocity = (float)(finalPosition - initialPosition)/(float)this.duration;
		//System.out.println("Velocity: "+initialVelocity);
		//System.out.println("selectedMovementType: "+selectedMovementType);
	}
	
	public void setSmoothFinal(int x1,int duration,char positionComponent) {
		selectedMovementType = movementTypes[2];
		this.duration = duration - smoothCompensation;
		this.positionComponent = positionComponent;
		componentX = objetiveElement.getLocation().x;
		componentY = objetiveElement.getLocation().y;
		if(positionComponent == 'x')
			initialPosition = componentX;
		else
			initialPosition = componentY;
		finalPosition = x1;
		difference = finalPosition - initialPosition;
		actualPosition = initialPosition;
		increase = initialPosition < finalPosition;
		//System.out.println("increase: "+increase);
	}
	
	private boolean notReachedToFinalPosition() {
		return increase? actualPosition < finalPosition:actualPosition > finalPosition;
	}
	
	public void setSlowedDownMovement(int x1, int duration, float percentage, char positionComponent) {
		selectedMovementType = movementTypes[2];
		this.duration = duration; //- compensation;
		this.positionComponent = positionComponent;
		
		componentX = objetiveElement.getLocation().x;
		componentY = objetiveElement.getLocation().y;
		if(positionComponent == 'x')
			initialPosition = componentX;
		else
			initialPosition = componentY;
		actualPosition = initialPosition;
		finalPosition = x1;
		
		halfTime = duration * percentage;
		float totalTime = ((((pow2(duration)/2)-halfTime*duration)/(halfTime-duration)) + duration + (-pow2(halfTime)/(2*(duration-halfTime))));
		initialVelocity = (float)(finalPosition - initialPosition)/totalTime;
		//System.out.println("Velocity: "+initialVelocity);
		//System.out.println("selectedMovementType: "+selectedMovementType);
	}
	
	public void setAcceleratedMovement(int x1,int duration, int acceleration, char positionComponent) {
		selectedMovementType = movementTypes[1];
		
		this.duration = duration - compensation;
		this.positionComponent = positionComponent;
		componentX = objetiveElement.getLocation().x;
		componentY = objetiveElement.getLocation().y;
		if(positionComponent == 'x')
			initialPosition = componentX;
		else
			initialPosition = componentY;
		finalPosition = x1;
		initialVelocity = (float)(finalPosition - initialPosition)/(float)this.duration;
		//System.out.println("Velocity: "+initialVelocity);
		//System.out.println("selectedMovementType: "+selectedMovementType);
	}
	
	@Override
	public void run() {
		switch(selectedMovementType) {
		case "uniform":
			uniformMovement();
			break;
		case "accelerated":
			acceleratedMovement();
			break;
		case "slowedDown":
			slowedDown();
			break;
		case "custom":
			smoothFinal();
			break;
		}
			 
	}
	
	private boolean movementToFinalPosition(float initialPos,float actualPos,float finalPos) {
		//System.out.println("from actual: "+(actualPos - initialPos)+", From final: "+(finalPos - initialPos));
		return Math.abs(actualPos - initialPos) < Math.abs(finalPos - initialPos);
	}
	
	private float pow2(float base) {
		return base*base;
	}
	
	private void slowedDown() {
		long mil = System.currentTimeMillis();
		while(movementToFinalPosition(initialPosition,actualPosition,finalPosition)) {
			
			//actualPosition = (actualPosition > finalPosition)? finalPosition:initialVelocity * time + initialPosition;
			
			if(time < halfTime) {
				actualPosition = initialVelocity * time + initialPosition;
			}
			else {
				actualPosition = initialVelocity*( ((pow2(time)/2)-halfTime*time)/(halfTime - duration) + time - (pow2(halfTime)/(2*(duration-halfTime))) ) + initialPosition;
			}
			actualPosition = Math.round(actualPosition);
			//System.out.println("Position: "+actualPosition);
			time += timeInterval;
			if(positionComponent == 'x')
				objetiveElement.setLocation((int)actualPosition,componentY);
			else
				objetiveElement.setLocation(componentX,(int)actualPosition);
			pause(timeInterval);
		}
		long c = System.currentTimeMillis()-mil;
		//System.out.println("Duration: "+c);
		objetiveElement.setLocation(componentX,(int)finalPosition);
	}
	
	private void smoothFinal() {
		long mil = System.currentTimeMillis();
		while(actualPosition > finalPosition) {
			//System.out.println("entro");
			double ecuation =  difference * (1 - Math.exp(time*( Math.log(1-percentage)/duration))) + 0;
			actualPosition = (actualPosition < finalPosition)? finalPosition:actualPosition+(float)ecuation;
			actualPosition = Math.round(actualPosition);
			//System.out.println("position: "+actualPosition);
			time += timeInterval;
			if(positionComponent == 'x')
				objetiveElement.setLocation((int)actualPosition,componentY);
			else
				objetiveElement.setLocation(componentX,(int)actualPosition);
			pause(timeInterval);
			if(actualPosition < finalPosition) break;
		}
		//long c = System.currentTimeMillis()-mil;
		//System.out.println("Millis: "+c);
		//System.out.println("Location: "+objetiveElement.getLocation().y);
		//System.out.println("final pos: "+finalPosition);
		objetiveElement.setLocation(componentX,(int)finalPosition);
	}
	
	private void uniformMovement() {
		//long mil = System.currentTimeMillis();
		while(actualPosition < finalPosition) {
			actualPosition = (actualPosition > finalPosition)? finalPosition:initialVelocity * time + initialPosition;
			actualPosition = Math.round(actualPosition);
			//System.out.println("position: "+actualPosition);
			time += timeInterval;
			if(positionComponent == 'x')
				objetiveElement.setLocation((int)actualPosition,componentY);
			else
				objetiveElement.setLocation(componentX,(int)actualPosition);
			pause(timeInterval);
		}
		//long c = System.currentTimeMillis()-mil;
		//System.out.println(c);
	}
	
	private void acceleratedMovement() {
		
	}
	
	private static void pause(long timeInMilliSeconds) {
	    long timestamp = System.currentTimeMillis();
	    do {
	    } while (System.currentTimeMillis() < timestamp + timeInMilliSeconds);
	}
	
}
