package org.firstinspires.ftc.teamcode.NewCode.V2_library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class DriveV2_Impl implements DriveV2 {
    public DcMotorEx frontRightDrive = null;
    public DcMotorEx frontLeftDrive = null;
    public DcMotorEx backRightDrive = null;
    public DcMotorEx backLeftDrive = null;

    public Servo frontRightServo = null;
    public Servo frontLeftServo = null;
    public Servo backRightServo = null;
    public Servo backLeftServo = null;

    public BNO055IMU bno055IMU = null;

    public void initDrive(HardwareMap hardwareMap) {
        frontRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("front right drive");
        frontRightDrive.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRightServo = hardwareMap.servo.get("front right servo");
        frontRightServo.scaleRange(0.2, 0.8);
        frontRightServo.setDirection(Servo.Direction.FORWARD);

        frontLeftDrive = (DcMotorEx) hardwareMap.dcMotor.get("front left drive");
        frontLeftDrive.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftServo = hardwareMap.servo.get("front left servo");
        frontLeftServo.scaleRange(0.2, 0.8);
        frontLeftServo.setDirection(Servo.Direction.FORWARD);

        backRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("back right drive");
        backRightDrive.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRightServo = hardwareMap.servo.get("back right servo");
        backRightServo.scaleRange(0.2, 0.8);
        backRightServo.setDirection(Servo.Direction.FORWARD);

        backLeftDrive = (DcMotorEx) hardwareMap.dcMotor.get("back right drive");
        backLeftDrive.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
        backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeftServo = hardwareMap.servo.get("back left servo");
        backLeftServo.scaleRange(0.2, 0.8);
        backLeftServo.setDirection(Servo.Direction.FORWARD);
    }

    public void init_bno055IMU (HardwareMap hardwareMap) {
        bno055IMU = hardwareMap.get( BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = null;
        parameters.gyroPowerMode = BNO055IMU.GyroPowerMode.NORMAL;
        parameters.gyroBandwidth = BNO055IMU.GyroBandwidth.HZ32;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.COMPASS;
        bno055IMU.initialize(parameters);
    }

    public void setDriveSpeed (double power){
        frontRightDrive.setPower(power);
        frontLeftDrive.setPower(power);
        backRightDrive.setPower(power);
        backLeftDrive.setPower(power);
    }

    public void setDriveSpeed (double frontRight, double backRight, double frontLeft, double backLeft) {
        frontRightDrive.setPower(frontRight);
        frontLeftDrive.setPower(frontLeft);
        backRightDrive.setPower(backRight);
        backLeftDrive.setPower(backLeft);
    }

    private void setServoPosition (double position) {
        frontRightServo.setPosition(position);
        frontLeftServo.setPosition(position);
        backRightServo.setPosition(position);
        backLeftServo.setPosition(position);
    }

    public driveServoState servoState (driveServoState state) {
        switch (state) {
            case RETRACT:
                setServoPosition(0.5); // wheels are straight up
                break;

            // TODO: 3/16/2019 reprogram servos for more travel
            case STRAIGHT:
                setServoPosition(1); // wheels are straight down, may be 0
                break;

            // TODO: 3/16/2019 add DRIVE case

            default:
                break;

        }
        return state;
    }
}
