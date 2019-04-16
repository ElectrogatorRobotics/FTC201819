package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
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

    private final static double MIN_SERVO_SCALE_VALUE = 0.18;

    public DriveV2_Impl(){}

    public void initDrive(HardwareMap hardwareMap) {
        frontRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("front right drive");
//        frontRightDrive.setMotorType(MotorConfigurationType.getMotorType(RevHD_VP_20_1.class));
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRightServo = hardwareMap.servo.get("front right servo");
        frontRightServo.scaleRange(MIN_SERVO_SCALE_VALUE, 0.8);
        frontRightServo.setDirection(Servo.Direction.FORWARD);

        frontLeftDrive = (DcMotorEx) hardwareMap.dcMotor.get("front left drive");
//        frontLeftDrive.setMotorType(MotorConfigurationType.getMotorType(RevHD_VP_20_1.class));
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftServo = hardwareMap.servo.get("front left servo");
        frontLeftServo.scaleRange(MIN_SERVO_SCALE_VALUE, 0.8);
        frontLeftServo.setDirection(Servo.Direction.FORWARD);

        backRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("back right drive");
//        backRightDrive.setMotorType(MotorConfigurationType.getMotorType(RevHD_VP_20_1.class));
        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRightServo = hardwareMap.servo.get("back right servo");
        backRightServo.scaleRange(MIN_SERVO_SCALE_VALUE, 0.8);
        backRightServo.setDirection(Servo.Direction.FORWARD);

        backLeftDrive = (DcMotorEx) hardwareMap.dcMotor.get("back left drive");
//        backLeftDrive.setMotorType(MotorConfigurationType.getMotorType(RevHD_VP_20_1.class));
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeftServo = hardwareMap.servo.get("back left servo");
        backLeftServo.scaleRange(MIN_SERVO_SCALE_VALUE, 0.8);
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

    public void setServoPosition (double frontPosition, double backPosition) {
        frontRightServo.setPosition(frontPosition);
        frontLeftServo.setPosition(frontPosition);
        backRightServo.setPosition(backPosition);
        backLeftServo.setPosition(backPosition);
    }

    // TODO: 3/28/2019 fix servo positions
    public driveServoState driveServoState(driveServoState state) {
        switch (state) {
            case RETRACT:
                setServoPosition(0.5, 0.5); // wheels are straight up
                break;

            case STRAIGHT:
                setServoPosition(0.07, .0); // wheels are straight down
                break;

            case DRIVE:
                setServoPosition(0.02419,0.02419); // wheels are out at ~30deg
            default:
                break;

        }
        return state;
    }
}
