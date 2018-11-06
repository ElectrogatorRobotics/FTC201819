package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Luke on 10/1/2017.
 */

public class ElectorgatorHardware {
    // declare all motors and sensors here:
    public DcMotor frontRightDrive = null;
    public DcMotor frontLeftDrive  = null;
    public DcMotor backRightDrive  = null;
    public DcMotor backLeftDrive   = null;
//    public DcMotor liftMotor        = null;

	public Servo jewelServo = null;
//	public Servo leftClaw   = null;
//	public Servo rightClaw  = null;

    public ColorSensor jewelColorSensor = null;
    public BNO055IMU imu                = null;

    public Orientation orientation;
    public Acceleration acceleration;

    HardwareMap hardwareMap = null;

    public ElectorgatorHardware (){}

	public void initLifter (HardwareMap hardware) {
		hardwareMap = hardware;

//		liftMotor = hardwareMap.dcMotor.get("arm");
//		leftClaw  = hardwareMap.servo.get("left claw");
//		rightClaw = hardwareMap.servo.get("right claw");

//		leftClaw.setPosition(0.5);
//		rightClaw.setPosition(0.5);
//		liftMotor.setPower(0.0);

//		rightClaw.setDirection(Servo.Direction.REVERSE);
//		liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

		//liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

	}

	public void initJewelDetection (HardwareMap hardware) {
		hardwareMap = hardware;

		jewelColorSensor = hardwareMap.get(ColorSensor.class, "jewel color sensor");
		jewelServo = hardware.servo.get("jewel color servo");
	}

    public void initMotors (HardwareMap hardware) {
        hardwareMap = hardware;

        // initialize motors
        frontRightDrive = hardwareMap.dcMotor.get("front right drive");
        frontLeftDrive  = hardwareMap.dcMotor.get("front left drive");
        backRightDrive  = hardwareMap.dcMotor.get("back right drive");
        backLeftDrive   = hardwareMap.dcMotor.get("back left drive");

        // set speed
        frontRightDrive.setPower(0.0);
        frontLeftDrive.setPower(0.0);
        backRightDrive.setPower(0.0);
        backLeftDrive.setPower(0.0);


        // set direction
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
	    frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
	    backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

	    // set mode
        // TODO: 11/9/2017 set drive mode to RUN_USING_ENCODER once the encoders are hocked up
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void initIMU (HardwareMap hardware) {
        hardwareMap = hardware;

        // initialise the IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // setup the accelerometer
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.accelPowerMode = BNO055IMU.AccelPowerMode.NORMAL;
        parameters.accelBandwidth = BNO055IMU.AccelBandwidth.HZ62_5;
        // setup the gyro
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.gyroPowerMode = BNO055IMU.GyroPowerMode.FAST;
        parameters.gyroBandwidth = BNO055IMU.GyroBandwidth.HZ32;
        parameters.gyroRange = BNO055IMU.GyroRange.DPS2000;
        // setup the magnetometer
        parameters.magPowerMode = BNO055IMU.MagPowerMode.FORCE;
        parameters.magRate = BNO055IMU.MagRate.HZ20;
        // setup the calibration files and logging
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
}