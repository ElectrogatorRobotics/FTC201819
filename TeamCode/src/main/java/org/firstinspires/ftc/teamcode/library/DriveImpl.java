package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

/**
 * Created by cameron.czekai on 11/1/2017.
 */

public class DriveImpl implements Drive {
    public DcMotor frontRightDrive = null;
    public DcMotor frontLeftDrive  = null;
    public DcMotor backRightDrive  = null;
    public DcMotor backLeftDrive   = null;
    Telemetry LOG;

    Proportional proportional = new Proportional();
	Gamepad gamepad1 = new Gamepad();
	Gamepad gamepad2 = new Gamepad();
	ElectorgatorHardware robot = new ElectorgatorHardware();

	/**
	 * This is the minimum power that the drive train can move
	 */
	public static final double MIN_SPEED = 0.4;

    /**
     * Calculate the number of ticks per inch of the wheel
     *
     * (Wheel diameter * PI) *  ticks per wheel regulation
     * wheel diameter = 4 inches
     * ticks per revolution of wheel = 7 cunts per motor revulsion * 20 gearbox reduction (20:1)
     */
    public static final double ENCODER_TICKS_PER_INCH = (4 * Math.PI) * (7 * 20);

    public DriveImpl(){}

    public DriveImpl(HardwareMap hwm, Telemetry telem){
        setTelemetry(telem);
        initMotors(hwm);
        robot.initIMU(hwm);
    }

    public void setTelemetry(Telemetry telem){
        LOG = telem;
    }

    public void initMotors (HardwareMap hardwareMap) {
        // initialize motor
        LOG.addLine("InitMotors");
        LOG.update();
        frontRightDrive = hardwareMap.dcMotor.get("front right drive");
        frontLeftDrive  = hardwareMap.dcMotor.get("front left drive");
        backRightDrive  = hardwareMap.dcMotor.get("back right drive");
        backLeftDrive   = hardwareMap.dcMotor.get("back left drive");

        // set speed
        LOG.addLine("SetPower");
        frontRightDrive.setPower(0.0);
        frontLeftDrive.setPower(0.0);
        backRightDrive.setPower(0.0);
        backLeftDrive.setPower(0.0);

        setMotorDriveDirection(MoveMethod.FORWARD);

        // set mode
        // TODO: 11/9/2017 set drive mode to RUN_USING_ENCODER once the encoders are hocked up
        LOG.addLine("NoEncoders. Disabling");
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LOG.update();
    }

    public void setMotorDriveDirection(MoveMethod system){
        // set direction
        LOG.addData("SettingDrive", system);
        if(system == MoveMethod.TURN) {
            frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else{
            frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

	/**
	 * Return the average position of the robot in the X axes
	 * @return
	 */
	public double getDriveX () {
		return (frontLeftDrive.getCurrentPosition() + frontRightDrive.getCurrentPosition() +
				backLeftDrive.getCurrentPosition() + backRightDrive.getCurrentPosition()) / 4;
	}

	/**
	 * Autonomous Methods:
	 */

	/**
	 * Move the drive to a precise location and orientation in one move.
	 * @param xInches
	 * @param yInches
	 * @param rotationDegrees
	 */
	public void driveToCord (double xInches, double yInches, double rotationDegrees) {
//		while () {

//		}
	}

	public void driveByTime(int milliseconds, Proportional.ProportionalMode driveMotor){
        LOG.addData("DriveByTime",milliseconds);
		ElapsedTime runtime = new ElapsedTime();
		double time;
        do {
            time = runtime.milliseconds();
			frontLeftDrive.setPower(proportional.p((float)time, milliseconds, driveMotor));
			frontRightDrive.setPower(proportional.p((float)time, milliseconds, driveMotor));
			backLeftDrive.setPower(proportional.p((float)time, milliseconds, driveMotor));
			backRightDrive.setPower(proportional.p((float)time, milliseconds, driveMotor));
		} while (time < milliseconds);
        LOG.addLine("ShutdownMotors");
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        LOG.update();
	}


    /**
     * @param targetDist  distance to drive in inches
     * @param driveMotor  Proportional.ProportionalMode for how to drive the motors
     */
    private double claculateDriveSpeed (double targetDist, double curPos, Proportional.ProportionalMode driveMotor){
	    double target = curPos + (targetDist * ENCODER_TICKS_PER_INCH);
	    double motorPower;

        do {
            // calculate the speed of the motor proportionally using the distance form the target
	        motorPower = proportional.p((float)targetDist, (float)curPos, driveMotor);
        } while (curPos < target);
	    return motorPower;
    }

    public void driveToTarget (double distance) {
        frontLeftDrive.setPower(claculateDriveSpeed(distance, backLeftDrive.getCurrentPosition(), Proportional.ProportionalMode.LEFT));
        frontRightDrive.setPower(claculateDriveSpeed(distance, backRightDrive.getCurrentPosition(), Proportional.ProportionalMode.RIGHT));
        backLeftDrive.setPower(claculateDriveSpeed(distance, backLeftDrive.getCurrentPosition(), Proportional.ProportionalMode.LEFT));
        backRightDrive.setPower(claculateDriveSpeed(distance, backRightDrive.getCurrentPosition(), Proportional.ProportionalMode.RIGHT));

    }

    public void turnToDegree (double angle) {
        // get rhe rotational z value to use for orientation
        double rotationalZ = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

        frontLeftDrive.setPower(claculateDriveSpeed(angle, rotationalZ, Proportional.ProportionalMode.LEFT));
        frontRightDrive.setPower(claculateDriveSpeed(angle, rotationalZ, Proportional.ProportionalMode.RIGHT));
        backLeftDrive.setPower(claculateDriveSpeed(angle, rotationalZ, Proportional.ProportionalMode.LEFT));
        backRightDrive.setPower(claculateDriveSpeed(angle, rotationalZ, Proportional.ProportionalMode.RIGHT));
    }

    public double setMotorSpeed (double speed, MotorControlMode controlMode, double expoBase){
	    switch (controlMode){
		    case EXPONENTIAL_CONTROL:
			    return Math.pow(Range.clip(speed, -1.0, 1.0), expoBase);
		    case LINEAR_CONTROL:
			    return Range.clip(speed, -1.0, 1.0);
			default:
				return 0;
	    }
    }

	public double setMotorSpeed (double speed, MotorControlMode controlMode){
		return setMotorSpeed(speed, controlMode, 5);
	}

	public double setMotorSpeedWithThrottle (double speed, MotorControlMode controlMode, double throttle){
		switch (controlMode){
			case EXPONENTIAL_CONTROL:
				return Math.pow(Range.clip(speed * throttleControl(throttle, MIN_SPEED), -1.0, 1.0), 5);

			case LINEAR_CONTROL:
				return Range.clip(speed *= throttleControl(throttle, MIN_SPEED), -1.0, 1.0);
			default:
				return 0;
		}
	}

	public double throttleControl (double throttle, double minValue) {
		if (throttle > minValue)
			minValue = throttle;

		return minValue;
	}

	public enum MotorControlMode {EXPONENTIAL_CONTROL, LINEAR_CONTROL}

	public enum ThrottleControl {LEFT_TRIGGER, RIGHT_TRIGGER}

    public enum MoveMethod{FORWARD, TURN}

	public void forward(int inches){
        setMotorDriveDirection(MoveMethod.FORWARD);
//        driveToTarget(inches, Proportional.ProportionalMode.NONE );
	}

	public void turn(double angle){
        //need to come up with a way to handle turning. Kinda an issue.
        //setMotorDriveDirection(MoveMethod.TURN);
        //driveByTime((int)angle, Proportional.ProportionalMode.NONE);
    }

    public void forward_time(int milliseconds){
        //driveToTarget(inches, Proportional.ProportionalMode.NONE );
        LOG.addLine("Forward!");
        setMotorDriveDirection(MoveMethod.FORWARD);
        driveByTime(milliseconds, Proportional.ProportionalMode.NONE );
    }

    public void turn_time(int milliseconds){
        //need to come up with a way to handle turning. Kinda an issue.
        setMotorDriveDirection(MoveMethod.TURN);
        driveByTime(milliseconds, Proportional.ProportionalMode.NONE);
    }

    public void shutdown () {
        backRightDrive.close();
        backLeftDrive.close();
        frontLeftDrive.close();
        frontRightDrive.close();
    }
}
