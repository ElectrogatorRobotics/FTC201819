package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by cameron.czekai on 11/1/2017.
 * edited by mira.eschliman in 18
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
    public static final double ENCODER_TICKS_PER_SLIDE = ((20.625 * 7)/(4 * Math.PI));
    public static final double ENCODER_TICKS_PER_INCH = ((20.625 * 7)/(4 * Math.PI));
    public static final double ENCODER_TICKS_PER_ANGLE = ((20.625 * 7)/(4 * Math.PI));

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

        setMotorDriveDirection(MoveMethod.STRAIGHT);

        // set mode
        setMotorToPositionAndReset();

    }

    //region SET MOTOR BEHAVIOR

    public void setMotorNoEncoders(){
        LOG.addLine("NoEncoders. Disabling");
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LOG.update();
    }

    public void setMotorToPositionAndReset(){
        LOG.addLine("Reset and Enable Encoders.");
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LOG.update();
    }

    public void setMotorWithEncoderAndReset(){
        LOG.addLine("Reset and Enable Encoders.");
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LOG.update();
    }

    public void setMotorDriveDirection(MoveMethod system){
        // set direction
        LOG.addData("SettingDrive", system);
        if(system == MoveMethod.SLIDE) {
            frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else if(system == MoveMethod.TURN){
            frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else {
            frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    //endregion

    private void setPower(double power){
        setMotorToPositionAndReset();
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower (power);
    }

	public void forward(int inches){
       setMotorDriveDirection(MoveMethod.STRAIGHT);
        int ticks = (int)Math.round(inches * ENCODER_TICKS_PER_INCH);
        frontLeftDrive.setTargetPosition(-1*ticks);
        frontRightDrive.setTargetPosition(ticks);
        backRightDrive.setTargetPosition(ticks);
        backLeftDrive.setTargetPosition(-1*ticks);
        setPower( .5);
	}

	public void turn(double angle){
        setMotorDriveDirection(MoveMethod.TURN);
        int ticks = (int)Math.round(angle * ENCODER_TICKS_PER_ANGLE);
        frontLeftDrive.setTargetPosition(ticks);
        frontRightDrive.setTargetPosition(ticks);
        backRightDrive.setTargetPosition(ticks);
        backLeftDrive.setTargetPosition(ticks);
        setPower(.5);
    }

    public void slide (double distnce){
        setMotorDriveDirection(MoveMethod.SLIDE);
        int ticks = (int)Math.round( distnce * ENCODER_TICKS_PER_SLIDE);
        frontLeftDrive.setTargetPosition(-1*ticks);
        frontRightDrive.setTargetPosition(-1*ticks);
        backRightDrive.setTargetPosition(ticks);
        backLeftDrive.setTargetPosition(ticks);
        setPower(.5);
    }

    public void forward_time(int milliseconds){
        //driveToTarget(inches, Proportional.ProportionalMode.NONE );
        LOG.addLine("Forward!");
        setMotorDriveDirection(MoveMethod.STRAIGHT);
        driveByTime(milliseconds, Proportional.ProportionalMode.NONE );
    }

    public void turn_time(int milliseconds){
        //need to come up with a way to handle turning. Kinda an issue.
        setMotorDriveDirection(MoveMethod.TURN);
        driveByTime(milliseconds, Proportional.ProportionalMode.NONE);
    }

//region HELPER FUNCTIONS

    public enum MotorControlMode {EXPONENTIAL_CONTROL, LINEAR_CONTROL}

    public enum ThrottleControl {LEFT_TRIGGER, RIGHT_TRIGGER}

    public enum MoveMethod{STRAIGHT, TURN, SLIDE}

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
    private double calculateDriveSpeed(double targetDist, double curPos, Proportional.ProportionalMode driveMotor){
        double target = curPos + (targetDist * ENCODER_TICKS_PER_INCH);
        double motorPower;

        do {
            // calculate the speed of the motor proportionally using the distance form the target
            motorPower = proportional.p((float)targetDist, (float)curPos, driveMotor);
        } while (curPos < target);
        return motorPower;
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

    public void shutdown () {
        backRightDrive.close();
        backLeftDrive.close();
        frontLeftDrive.close();
        frontRightDrive.close();
    }
//endregion
}
