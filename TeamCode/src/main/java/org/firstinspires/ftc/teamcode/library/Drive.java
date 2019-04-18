package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by cameron.czekai on 11/1/2017.
 */

public interface Drive {
	double MIN_SPEED = 0.4;

	void setTelemetry(Telemetry telem);
	void passLinearOp(LinearOpMode lop);

	void forward(double inches);

    /**
     * positive angles turn clockwise
     * @param angle
     */
    void turn(double angle);

    void slide(double inches);

	/**
	 * Thees are the motor control modes that we can use
	 */
	enum MotorControlMode {
		EXPONENTIAL_CONTROL, LINEAR_CONTROL
	}

	enum ThrottleControl {LEFT_TRIGGER, RIGHT_TRIGGER}

	enum MoveDirection {FORWARD, RIGHT, BACK, LEFT}

	enum MotorMode{NONE,POSITION}
}
