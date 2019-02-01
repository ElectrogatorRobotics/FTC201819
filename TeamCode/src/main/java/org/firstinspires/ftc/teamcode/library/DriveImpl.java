package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by cameron.czekai on 11/1/2017.
 * edited by mira.eschliman in 18
 */

public class DriveImpl implements Drive {
    public DcMotorEx frontRightDrive = null;
    public DcMotorEx frontLeftDrive  = null;
    public DcMotorEx backRightDrive  = null;
    public DcMotorEx backLeftDrive   = null;
    Telemetry LOG;

    private static final double DRIVE_POWER = .3;
    private static final double TURN_POWER = .25;
    private static final double TURN_THRESHOLD = .5;

    private ElapsedTime runtime = new ElapsedTime();

    public BNO055IMU imu = null;
    public Orientation angle = null;

	/**
	 * This is the minimum power that the drive train can move
	 */

    private static final int MAX_SPEED = (6000/360);
	public static final double MIN_SPEED = 0.4;

    /**
     * Calculate the number of ticks per inch of the wheel
     *
     * (Wheel diameter * PI) *  ticks per wheel regulation
     * wheel diameter = 4 inches
     * ticks per revolution of wheel = 7 counts per motor revolution * 20 gearbox reduction (20:1)
     */
    public static final double ENCODER_COUNTS_PER_INCH = (28*20.625)/(Math.PI*4);

    public enum MoveMethod{STRAIGHT, TURN, SLIDE, DEPLOY}

    public DriveImpl(){}
    public DriveImpl(HardwareMap hwm, Telemetry telem){
        setTelemetry(telem);
        initMotors(hwm);
        initialiseIMU(hwm);
    }

    public void setTelemetry(Telemetry telem){
        LOG = telem;
    }
    public void initMotors (HardwareMap hardwareMap) {
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

    //region SET MOTOR BEHAVIOR

    public void setMotorBehavior(MotorMode mode){
        switch (mode){
            case POSITION:
                setMotorToByPosition();
                break;
            case NONE:
            default:
                setMotorNoEncoders();
                break;
        }
    }

    public void setMotorNoEncoders(){
        LOG.addLine("NoEncoders. Disabling");
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LOG.update();
    }

    public void setMotorToPositionAndReset(){
        LOG.addLine("Reset Encoders.");
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorToByPosition();
        LOG.update();
    }

    public void setMotorToByPosition(){
        LOG.addLine("Enable Encoders.");
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LOG.update();
    }

    public void setMotorDriveDirection(MoveMethod system){
        // set direction
        LOG.addData("SettingDrive", system);
        switch(system) {
            case TURN:
                frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case SLIDE:
                frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case DEPLOY:
                frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case STRAIGHT:
            default:
                frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
        }
    }

    public void setTargetPosition (int targetPosition) {
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + targetPosition);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + targetPosition);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + targetPosition);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + targetPosition);
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

    //endregion

	public void forward(int inches){
        setMotorDriveDirection(MoveMethod.STRAIGHT);
        int ticks = (int)Math.round(inches * ENCODER_COUNTS_PER_INCH);
        setTargetTolerance(50);
        setTargetPosition(ticks);
        driveByPosition(DRIVE_POWER);
	}

	public void turn(double angle){
        setMotorDriveDirection(MoveMethod.TURN);
        turnToAngle(angle);
    }

    public void slide (double distance){
        setMotorDriveDirection(MoveMethod.SLIDE);
        int ticks = (int)Math.round( distance * ENCODER_COUNTS_PER_INCH);
        setTargetTolerance(50);
        setTargetPosition(ticks);
        driveByPosition(DRIVE_POWER);
    }

    public void forward_time(int milliseconds){
        double power = 0.7;
        if(milliseconds<0) {
            power *= -1;
        }
        //driveToTarget(inches, Proportional.ProportionalMode.NONE );
        LOG.addLine("Forward!");
        setMotorDriveDirection(MoveMethod.STRAIGHT);
        driveByTime(milliseconds, power );
    }

    public void turn_time(int milliseconds){
        double power = 0.7;
        if(milliseconds<0) {
            power *= -1;
        }
        //need to come up with a way to handle turning. Kinda an issue.
        setMotorDriveDirection(MoveMethod.TURN);
        driveByTime(milliseconds, power);
    }

    public void slide_time(int milliseconds){
        double power = 0.7;
        if(milliseconds<0) {
            power *= -1;
        }
        //need to come up with a way to handle turning. Kinda an issue.
        setMotorDriveDirection(MoveMethod.SLIDE);
        driveByTime(milliseconds, power);
    }

//region HELPER FUNCTIONS

    /**
     * Return the average position of the robot in the X axes
     * @return
     */
    @Deprecated
    public double getDriveX () {
        return (frontLeftDrive.getCurrentPosition() + frontRightDrive.getCurrentPosition() +
                backLeftDrive.getCurrentPosition() + backRightDrive.getCurrentPosition()) / 4;
    }

    /**
	 * Autonomous Methods:
	 */
	public void driveByTime(int milliseconds, double power){
        LOG.addData("DriveByTime",milliseconds);
        ElapsedTime runtime = new ElapsedTime();
        milliseconds = Math.abs(milliseconds);
        double time;
        do {
            time = runtime.milliseconds();
        	Thread.yield(); //effectively what the LinearOpMode idle call does
		} while (time < milliseconds);
        LOG.addLine("ShutdownMotors");
        stop();
        LOG.update();
    }

    /**
     * Autonomous Methods:
     */
    public void driveByPosition(double power){
        LOG.addData("DriveByPosition pow=",power);
        LOG.addData("DriveByPosition targ=",frontRightDrive.getTargetPosition());
        setMotorToByPosition();
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower (power);
        do {
            LOG.addData("At position ",frontRightDrive.getCurrentPosition());
            LOG.update();
            Thread.yield(); //effectively what the LinearOpMode idle call does
        } while (frontRightDrive.isBusy());
        stop();
        LOG.update();
    }

    public double turnToAngle (double angleToTurn) {
        angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        /**
         * set {@link angleToTurn} equal to the {@link imu}'s Z axes
         */

        double targetAngle = (angle.thirdAngle + angleToTurn + 360)%360 ;
        double power = TURN_POWER;
        if(Math.sin(angle.thirdAngle - targetAngle) < 0) power *= -1;
        setMotorBehavior(MotorMode.NONE);
        LOG.addLine("Turning");
        runtime.reset();
        while(Math.abs(angle.thirdAngle - targetAngle) > TURN_THRESHOLD && runtime.seconds() < 10 ){
            angle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            LOG.addData("Turn target ",targetAngle);
            LOG.addData("Turn current ",angle.thirdAngle);
            LOG.update();
        }
        stop();
        return angleToTurn;
    }

    public double setMotorSpeed (double speed, MotorControlMode controlMode) {
        switch (controlMode) {
//            case EXPONENTIAL_CONTROL:
//                return Math.pow(Range.clip(speed, -1.0, 1.0), expoBase);
            case LINEAR_CONTROL:
                return Range.clip(speed, -1.0, 1.0);
            default:
                return 0;
        }
    }

    public double throttleControl (double throttle, double minValue) {
        if (throttle > minValue)
            minValue = throttle;

        return minValue;
    }

	public void deploy_assist(){
        setMotorDriveDirection(MoveMethod.DEPLOY);
        setMotorPower(.3);
    }

    public void stop() {
        setMotorPower(0);
        setMotorDriveDirection(MoveMethod.STRAIGHT);
    }
    public void setMotorPower(double power){
        frontRightDrive.setPower(power);
        frontLeftDrive.setPower(power);
        backRightDrive.setPower(power);
        backLeftDrive.setPower(power);
    }

// this is not lit up in the Drive.java so i am so confused
    public void shutdown () {
        backRightDrive.close();
        backLeftDrive.close();
        frontLeftDrive.close();
        frontRightDrive.close();
    }
//endregion
}
