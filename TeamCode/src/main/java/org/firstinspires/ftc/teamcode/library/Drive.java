package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by cameron.czekai on 11/1/2017.
 */

public interface Drive {
	double MIN_SPEED = 0.4;

    void initMotors(HardwareMap hardware);

	void setTelemetry(Telemetry telem);

	double setMotorSpeed (double speed, MotorControlMode controlMode);

	void move(int inches, MoveDirection md);

    void turn(double angle, MoveDirection md);

	void move_time(int milliseconds, MoveDirection md);

	void turn_time(int milliseconds, MoveDirection md);

	void shutdown();

	/**
	 * Thees are the motor control modes that we can use
	 */
	enum MotorControlMode {
		EXPONENTIAL_CONTROL, LINEAR_CONTROL
	}

	enum ThrottleControl {LEFT_TRIGGER, RIGHT_TRIGGER}

	enum MoveDirection {FORWARD, RIGHT, BACK, LEFT}
}
