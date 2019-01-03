package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by cameron.czekai on 11/1/2017.
 */

public interface Drive {



    void initMotors(HardwareMap hardware);

	void setTelemetry(Telemetry telem);

	/**
	 * Set the speed of a motor with or with out expo.
	 * Use this method if the expo base needs to be less than or grater than 5
	 *
	 * @param speed
	 * @param controlMode
	 * @param expoBase
	 * @return
	 */
	double setMotorSpeed(double speed, DriveImpl.MotorControlMode controlMode, double expoBase);

	/**
	 * Set the speed of a motor with or with out expo.
	 * Use this method if the expo base needs to be 5
	 *
	 * @param speed
	 * @param controlMode
	 * @return
	 */
	double setMotorSpeed(double speed, DriveImpl.MotorControlMode controlMode);

	/**
	 *
	 * @param throttle
	 * @param minValue
	 * @return
	 */
	double throttleControl(double throttle, double minValue);

	/**
	 *
	 * @param speed
	 * @param controlMode
	 * @param throttle
	 * @return
	 */
	double setMotorSpeedWithThrottle(double speed, DriveImpl.MotorControlMode controlMode, double throttle);

	void forward(int inches);

	//this is used in Drive Impl.java so i dont know why it is not lit up
    void turn(double angle);

	void forward_time(int milliseconds);

	void deploy_assist();

	void stop();

	//can we get rid of this,because,I dont know what in the world it is for
	// shutdown is used to shut the motors and it is used in the DriveImpl.java so i am confused
	void shutdown();

	/**
	 * Thees are the motor control modes that we can use
	 */
	enum MotorControlMode {
		EXPO_CONTROL,
		LINEAR_CONTROL
	}

	enum ThrottleControl {
		LEFT_TRIGGER,
		RIGHT_TRIGGER
	}
}
