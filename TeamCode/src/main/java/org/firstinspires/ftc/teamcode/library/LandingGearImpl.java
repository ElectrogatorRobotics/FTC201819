package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LandingGearImpl implements LandingGear {

    public Servo frontRightServo = null;
    public Servo frontLeftServo = null;
    public Servo backRightServo = null;
    public Servo backLeftServo = null;
    private Telemetry log;
    private LinearOpMode lom;
    private Drive drivetrain;
    private ElapsedTime runtime = new ElapsedTime();

    private final static double MIN_SERVO_SCALE_VALUE = 0.18;
    private final static double MAX_SERVO_SCALE_VALUE = 0.8;

    double LEGS_BK_STRAIGHT = 0.15;
    double LEGS_FR_STRAIGHT = 0.11;

    double LEGS_RETRACT = 0.5;

    double LEGS_BK_DEPLOY = 0.0;
    double LEGS_FR_DEPLOY = 0.0;


    public LandingGearImpl(HardwareMap hm, Drive drive, LinearOpMode lop) {
        init(hm, drive, lop);
    }
    public void init(HardwareMap hm, Drive drive, LinearOpMode lop){
        frontRightServo = hm.servo.get("front right servo");
        frontRightServo.scaleRange(MIN_SERVO_SCALE_VALUE, MAX_SERVO_SCALE_VALUE);
        frontRightServo.setDirection(Servo.Direction.FORWARD);

        frontLeftServo = hm.servo.get("front left servo");
        frontLeftServo.scaleRange(MIN_SERVO_SCALE_VALUE, MAX_SERVO_SCALE_VALUE);
        frontLeftServo.setDirection(Servo.Direction.FORWARD);

        backRightServo = hm.servo.get("back right servo");
        backRightServo.scaleRange(MIN_SERVO_SCALE_VALUE, MAX_SERVO_SCALE_VALUE);
        backRightServo.setDirection(Servo.Direction.FORWARD);

        backLeftServo = hm.servo.get("back left servo");
        backLeftServo.scaleRange(MIN_SERVO_SCALE_VALUE, MAX_SERVO_SCALE_VALUE);
        backLeftServo.setDirection(Servo.Direction.FORWARD);

        drivetrain = drive;

        lom = lop;
    }

    public void stand_up(){
        setServoPosition(LEGS_FR_STRAIGHT, LEGS_BK_STRAIGHT);
    }

    public void retract(){
        setServoPosition(LEGS_RETRACT, LEGS_RETRACT);
    }

    public void deploy(){
        setServoPosition(LEGS_FR_DEPLOY, LEGS_BK_DEPLOY);
    }

    public void unhook(){
        stand_up();
        drivetrain.slide(4);
        deploy();
    }

    public double getState(){
        return backLeftServo.getPosition();
    }
    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs && lom.opModeIsActive()){
            Thread.yield();
        }
    }

    //debug functions
    public void setServoPosition (double frontPosition, double backPosition) {
        frontRightServo.setPosition(frontPosition);
        frontLeftServo.setPosition(frontPosition);
        backRightServo.setPosition(backPosition);
        backLeftServo.setPosition(backPosition);
    }

}
