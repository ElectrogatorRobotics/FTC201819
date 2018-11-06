package org.firstinspires.ftc.teamcode.library;

/**
 * Created by Luke on 10/13/2017.
 */

public class Proportional {
	double p (float target, float currentPos, ProportionalMode mode) {
		final double pGain = 1.0, midGain = 0.0;
		float targetError = target - currentPos;
		double proportional = targetError * pGain;
		double output = 0;

		if (mode == mode.LEFT) { // clip the output for motor control
			output = pGain + proportional;
			clipOutput(output);
		}
		if (mode == mode.RIGHT) { // clip the output for motor control
			output = pGain - proportional;
			clipOutput(output);
		}
		if (mode == mode.NONE) // don't clip the output when not using to set motor power
			output = pGain + proportional;
		return output;
	}

	private double clipOutput (double output) {
		return clip(output, -1.0, 1.0); // -1.0 is full reverse and 1.0 is full forward
	}

	private double clip(double number, double min, double max) {
		if (number < min) number = min;
		if (number > max) number = max;
		return number;
	}

	public enum ProportionalMode{LEFT, RIGHT, NONE}
}
