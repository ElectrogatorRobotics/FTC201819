package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class MotorControls {
    public DcMotorEx frontRightDrive = null;
    public DcMotorEx frontLeftDrive  = null;
    public DcMotorEx backRightDrive  = null;
    public DcMotorEx backLeftDrive   = null;

    public BNO055IMU imu = null;
    public Orientation angle = null;

    private static final int MAX_SPEED = (6000/360);
    public static final double ENCODER_COUNTS_PER_INCH = (28*20.625)/(Math.PI*4);

    public MotorControls() {}

    public void initaliseMotors(HardwareMap hardwareMap) {
        frontRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("front right drive");
        frontLeftDrive  = (DcMotorEx) hardwareMap.dcMotor.get("front left drive");
        backRightDrive  = (DcMotorEx) hardwareMap.dcMotor.get("back right drive");
        backLeftDrive   = (DcMotorEx) hardwareMap.dcMotor.get("back left drive");

        // turn on the break in the motors
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        // set the speed that the motors run at
//        setVelocity();
    }

    public void initialiseIMU(HardwareMap hardwareMap) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.gyroBandwidth = BNO055IMU.GyroBandwidth.HZ64;
        parameters.gyroPowerMode = BNO055IMU.GyroPowerMode.NORMAL;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void setMotorMode(DcMotor.RunMode runMode) {
        frontLeftDrive.setMode(runMode);
        frontRightDrive.setMode(runMode);
        backLeftDrive.setMode(runMode);
        backRightDrive.setMode(runMode);
    }

    public void setTargetPosition (int targetPosition) {
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + targetPosition);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + targetPosition);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + targetPosition);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + targetPosition);
    }

    void setVelocity () {
        frontRightDrive.setVelocity(MAX_SPEED,AngleUnit.DEGREES);
        frontLeftDrive.setVelocity(MAX_SPEED,AngleUnit.DEGREES);
        backRightDrive.setVelocity(MAX_SPEED,AngleUnit.DEGREES);
        backLeftDrive.setVelocity(MAX_SPEED,AngleUnit.DEGREES);
    }

    /**
     * tolerance is measured in encoder ticks
     * @param tolerance
     */
    void setTargetTolerance(int tolerance) {
        frontRightDrive.setTargetPositionTolerance(tolerance);
        frontLeftDrive.setTargetPositionTolerance(tolerance);
        backRightDrive.setTargetPositionTolerance(tolerance);
        backLeftDrive.setTargetPositionTolerance(tolerance);
    }

    public double setMotorSpeed(double speed) {
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        backRightDrive.setPower(speed);
        backLeftDrive.setPower(speed);

        return speed;
    }

    public void driveToTarget (double targetPositionInches, double speed) {
        // it is a good guess that all the motors have the same mode so we need only save the mode of one motor
        DcMotor.RunMode runMode = frontLeftDrive.getMode();
        setTargetTolerance(50);
        setTargetPosition((int) (targetPositionInches*ENCODER_COUNTS_PER_INCH));
        setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setMotorSpeed(speed);
        // if the motors have stopped, set the mode back to what they were in before
        if (!frontLeftDrive.isBusy()){
            setMotorMode(runMode);
        }
    }

    public double turnToAngle (double angleToTurn) {
        angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        /**
         * set {@link angleToTurn} equal to the {@link imu}'s Z axes
         */
        angleToTurn = angle.thirdAngle;


        return angleToTurn;
    }
}
