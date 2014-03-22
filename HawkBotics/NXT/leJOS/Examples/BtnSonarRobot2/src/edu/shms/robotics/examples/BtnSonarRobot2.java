package edu.shms.robotics.examples;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

/* 
 * PREAMBLE
 * 
 * The keyword "static" means "not attached to an object".  The "static" keyword implies
 * "attached to a class" and is automatically available to every object instance of that
 * class by specifying the class name such as BtnSonarRobot2.Robot for the Robot declaration 
 * below. 
 */

public class BtnSonarRobot2 {

	/*-
	 * ***************
	 * ** CONSTANTS **
	 * ***************
	 */
	final int MinimumRangeCM = 10; // cm
	final long DebounceTimeMS = 500;

	/*-
	 * *************************
	 * ** STATIC CLASS FIELDS **
	 * *************************
	 * The following field, Robot, is created (via "new") once and only once when this
	 * program first runs.
	 */
	public static BtnSonarRobot2 Robot = new BtnSonarRobot2();
	/*
	 * Create a single instance of **this** class to act as the robot object
	 * called "Robot". The scope is "public", so other modules can access it as
	 * needed. The "R" in "Robot" is capitalized because it is an object that
	 * exists at the class (aka module) level.
	 * 
	 * Why do this?
	 * 
	 * Every Java program starts with a "public static void main()" because
	 * there are no objects yet--the entry point of the program must be "static"
	 * because it's no attached to any created ("instantiated") object.
	 * 
	 * When we start creating mission objects, we'll ned to share the Robot
	 * object among those mission objects. If those missions want to control the
	 * robot, then the Robot's sensors and drive mechanism need to be
	 * accessible. This is called "encapsulation" which simply means the details
	 * of the object are "inside" the object as declared in its class.
	 */

	public static BtnSonarRobot2 RobotIgnored = new BtnSonarRobot2();
	/*
	 * This static instance is for example purposes only and is not needed for
	 * the proper functioning of this program. Delete this field and check it!
	 */

	/*-
	 * ****************************
	 * ** OBJECT INSTANCE FIELDS **
	 * ****************************
	 */
	/*
	 * The following fields are duplicated for each and every instance of
	 * BtnSonarRobot2 (see "new BtnSonarRobot2() above.)
	 * 
	 * To clarify, Robot and RobotIgnored are created once, but "button" and
	 * "sonar" (below) are created twice - once for Robot and again for
	 * RobotIgnored.
	 * 
	 * Every time there is a "new BtnSonarRobot2()" a copy of the fields below
	 * are created and embedded in that new copy;
	 * 
	 * Since there's only one robot, this seems like extra work, but it serves
	 * two purposes--good encapsulation and good abstraction which are explained
	 * later.
	 */
	TouchSensor button;
	UltrasonicSensor sonar;

	/*-
	 * These are the sensor "FIELDS" of the BtnSonarRobot2 object when it is
	 * instantiated (see 'new BtnSonarRobot2()' above.) They are NOT static
	 * because they are accessed in one of three ways:
	 *  1. inside Robot methods (non-static methods inside BtnSonarRobot2)
	 *  2. in another class via BtnSonarRobot2.Robot.button...
	 *      or BtnSonarRobot2.Robot.sonar...
	 *  3. in a static method in this class via Robot.button... or Robot.sonar... because
	 *     the class is already known.
	 */

	/********************************************************/
	/*
	 * ENTRY POINT of the PROGRAM
	 */
	public static void main(String[] args) { // ...NOTE: there's no "throws"
												// here anymore.
		Robot.setup();
		Robot.go();
		Robot.shutdown();
	}

	/********************************************************/
	public BtnSonarRobot2() { // constructor
		/*
		 * This is a constructor because there's no "void" or other type between
		 * "public" and the method name "BtnSonarRobot2", and the method name
		 * "BtnSonarRobot2" matches the class name.
		 */

		/*
		 * Initialize the pushbutton sensor.
		 */
		button = new TouchSensor(SensorPort.S1);

		/*
		 * Initialize the ultrasonic range sensor.
		 */
		sonar = new UltrasonicSensor(SensorPort.S4);
		sonar.continuous(); // ...force the sonar to continuously monitor the
		// distance.
	}

	/********************************************************/
	public void setup() {
		// ...nothing for now, but it's a good template.
	}

	/********************************************************/
	/*
	 * The go() method is the top-most behavior that controls all robot actions.
	 * The "steps" of the actions are abstracted by using descriptive method
	 * names and moving the details of the step inside that method.
	 * 
	 * Reading the lines below gives a very clear picture of what this robot is
	 * doing. This is called "self-documenting" code and is part of the art of
	 * programming.
	 */
	public void go() {
		while (true) {
			waitOnButtonPress();
			goForward();
			waitForStopEvents();
			stop();
		}
	}

	/********************************************************/
	private void waitOnButtonPress() {
		while (!isButtonPressedAndReleased()) {
		} // wait until pressed

		/*
		 * NOTE: isButtonPressed() is shared, so it is its own method.
		 */
	}

	/********************************************************/
	private void goForward() {
		Motor.A.forward();
		Motor.C.forward();
	}

	/********************************************************/
	private void stop() {
		Motor.A.stop();
		Motor.C.stop();
	}

	/********************************************************/
	private void waitForStopEvents() {

		while (!isButtonPressedAndReleased() && !isRangeTooNear()) {
		}
		// ...exiting this loop means the button is pressed or sonar is
		// closer than the threshold.
	}

	/********************************************************/
	private boolean isRangeTooNear() {
		return sonar.getDistance() < MinimumRangeCM;
	}

	/********************************************************/
	private boolean isButtonPressedAndReleased() {
		if (button.isPressed()) {
			Delay.msDelay(DebounceTimeMS);
			while (button.isPressed()) {
				/* wait for release */
			}
			return true;
		}
		return false;
	}

	/********************************************************/
	private void shutdown() {
		// ...nothing for now, but it's a good template.
	}
}
