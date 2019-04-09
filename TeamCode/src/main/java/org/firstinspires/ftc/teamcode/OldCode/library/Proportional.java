package org.firstinspires.ftc.teamcode.OldCode.library;

/**
 * Created by Luke on 10/13/2017.
 */

@Deprecated
public class Proportional {
	double p (double current, double target, double start_power, ProportionalMode mode) {
		final double pGain = 1.0;
		final double linear_step_per_ms = .01;

		double targetError = target - current;
		double proportional = targetError * pGain;
		double output = 0;

		if (mode == mode.LINEAR) { // clip the output for motor control
			output = pGain + proportional;
			clipOutput(output);
		}
		if (mode == mode.EXPONENTIAL) { // clip the output for motor control
			output = pGain - proportional;
			clipOutput(output);
		}
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

	public enum ProportionalMode{LINEAR, EXPONENTIAL}
}
