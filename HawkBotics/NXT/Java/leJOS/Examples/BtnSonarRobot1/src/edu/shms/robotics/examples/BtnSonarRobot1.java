package edu.shms.robotics.examples;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

public class BtnSonarRobot1 {

	public static void main(String[] args) throws Exception {

		TouchSensor button = new TouchSensor(SensorPort.S1);
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S4);

		sonar.continuous();
		
		while (true) {
			while (!button.isPressed()) {
			} // wait until pressed

			Motor.A.forward();
			Motor.C.forward();

			Thread.sleep(1000); //...give human time to release pushbutton!
			
			/*
			 * The following loop test checks two things in a negative sense: 1.
			 * Is the button NOT pressed, and... 2. Is the sonar NOT too close?
			 */
			while (! (button.isPressed() || (sonar.getDistance() < 10 /* cm */))) {
				// ...if we're inside this loop, then keep going.
			}
			// ...exiting this loop means the button is pressed or sonar is
			// closer than 10cm.

			Motor.A.stop();
			Motor.C.stop();
		}
	}
}
