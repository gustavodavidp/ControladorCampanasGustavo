package useful_classes;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
//import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
//import com.pi4j.io.gpio.digital.PullResistance;
//import com.pi4j.util.Console;


import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;

import java.util.concurrent.TimeUnit;

//import com.pi4j.*;

public class GpioComm {
    public static boolean finished = false;

	static DigitalState initialState = DigitalState.HIGH;
	static DigitalState shotdownState = DigitalState.HIGH;
	boolean inverted = true;
	// crear controlador gpio
	Context pi4j = Pi4J.newAutoContext();
	Console console;


	static DigitalOutput io4;
	static DigitalOutput io5;
	static DigitalOutput io6;
	static DigitalOutput io12;
	static DigitalOutput io17;
	static DigitalOutput io18;
	static DigitalOutput io22;
	static DigitalOutput io23;
	static DigitalOutput io24;
	static DigitalOutput io25;
	static DigitalOutput io27;

	public void initAll(){
		var config = DigitalOutput.newConfigBuilder(pi4j)
                .shutdown(shotdownState)
                .initial(initialState)
                .provider("pigpio-digital-output");

		io4 = pi4j.create(config.address(4));
		io5 = pi4j.create(config.address(5));
		io6 = pi4j.create(config.address(6));
		io12 = pi4j.create(config.address(12));
		io17 = pi4j.create(config.address(17));
		io18 = pi4j.create(config.address(18));
		io22 = pi4j.create(config.address(22));
		io23 = pi4j.create(config.address(23));
		io24 = pi4j.create(config.address(24));
		io25 = pi4j.create(config.address(25));
		io27 = pi4j.create(config.address(27));
	}
	public void initPin(int pin){
		//System.out.print("Started: ");
		System.out.println(pi4j);
		switch(pin){
			case 4:
			io4 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(4).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;
			case 5:
			io5 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(5).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 6:
			io6 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(6).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 12:
			io12 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(12).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 17:
			io17 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(17).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 18:
			io18 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(18).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 22:
			io22 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(22).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 23:
			io23 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(23).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 24:
			io24 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(24).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 25:
			io25 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(25).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;

			case 27:
			io27 = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
			.address(27).shutdown(shotdownState)
			.initial(initialState)
			.provider("pigpio-digital-output"));
			break;
		}
	}
	
	private DigitalOutput selectPin(String sys_pin){
		switch(sys_pin) {
		case "BACKLIGHT":
			return io4;
		case "RB":
			return io5;
		case "GND":
			return io6;
		case "CARRILLON":
			return io12;
		case "RA":
			return io17;
		case "TC":
			return io18;
		case "TA":
			return io23;
		case "BC":
			return io22;
		case "BB":
			return io24;
		case "BA":
			return io25;
		case "TB":
			return io27;
		default:
			return null;
		}
	}
	
	public void setHigh(String sys_pin) {
		if(inverted)
			selectPin(sys_pin).low();
		else
			selectPin(sys_pin).high();

		//System.out.println(sys_pin+" is high");
	}
	
	public void setLow(String sys_pin) {
		if(inverted)
			selectPin(sys_pin).high();
		else
			selectPin(sys_pin).low();
		//System.out.println(sys_pin+" is low");
	}
	
	public void setToggle(String sys_pin) {
		selectPin(sys_pin).toggle();
		//System.out.println(sys_pin+" toogle");
	}
	
	public void setPulse(String sys_pin, int miliseconds) {
		if(inverted)
			selectPin(sys_pin).pulseLow(miliseconds, TimeUnit.MILLISECONDS);
		else
			selectPin(sys_pin).pulseHigh(miliseconds, TimeUnit.MILLISECONDS);
		
		//System.out.println(sys_pin+" "+miliseconds+" pulse");
	}
	
	public void shutdown() {
		finished = true;
		pi4j.shutdown();
		System.out.print("After shutdown: ");
		System.out.println(pi4j);
	}
}
