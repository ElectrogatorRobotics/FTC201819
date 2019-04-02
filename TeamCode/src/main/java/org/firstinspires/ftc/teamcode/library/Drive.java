package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by cameron.czekai on 11/1/2017.
 */

public interface Drive {
	double MIN_SPEED = 0.4;

    void initMotors(HardwareMap hardware);
	void initialiseIMU(HardwareMap hardwareMap);
	void initTelemetry(Telemetry telem);
	void passLinearOp(LinearOpMode lop);

	/**
	 * Set the speed of a motor with or with out expo.
	 * Use this method if the expo base needs to be less than or grater than 5
	 *
	 * @param speed
	 * @param controlMode
	 * @param expoBase
	 * @return
	 */
	//double setMotorSpeed(double speed, DriveV2_Impl.MotorControlMode controlMode, double expoBase);

	/**
	 * Set the speed of a motor with or with out expo.
	 * Use this method if the expo base needs to be 5
	 *
	 * @param speed
	 * @param controlMode
	 * @return
	 */
	//double setMotorSpeed(double speed, DriveV2_Impl.MotorControlMode controlMode);

	void setMotorDriveDirection(DriveImpl.MoveMethod system);

	void setMotorBehavior(MotorMode mode);

	double setMotorSpeed(double speed, MotorControlMode mcm);

	void forward(double inches);
    void turn(double angle);
	void slide (double distnce);

	void forward_time(int milliseconds);
	void turn_time(int milliseconds);
	void slide_time(int milliseconds);

	void deploy_assist();
	void stop();

	void setupDriveForTeleop ();

	//can we get rid of this,because,I dont know what in the world it is for
	// shutdown is used to shut the motors and it is used in the DriveV2_Impl.java so i am confused
	void shutdown();

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
