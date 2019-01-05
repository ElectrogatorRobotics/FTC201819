package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LandingGearImpl implements LandingGear {

    public Servo frontRight;
    public Servo frontLeft;
    public Servo backRight;
    public Servo backLeft;
    private Telemetry log;
    private Drive drivesystem;
    private ElapsedTime runtime = new ElapsedTime();


    public void init(HardwareMap hm, Drive drivetrain){
        frontLeft = hm.servo.get("front left servo");
        frontRight = hm.servo.get("front right servo");
        backLeft = hm.servo.get("back left servo");
        backRight = hm.servo.get("back right servo");
        drivesystem = drivetrain;
    }

    private void stage(){
        frontRight.setPosition(LandingGear.LEGS_STAGE);
        frontLeft.setPosition(LandingGear.LEGS_STAGE);
        sleep(2000);
        backLeft.setPosition(LandingGear.LEGS_STAGE);
        backRight.setPosition(LandingGear.LEGS_STAGE);
        sleep(1000);
    }

    public void stand_up(){
        boolean da = false;
        if(frontLeft.getPosition()>LandingGear.LEGS_STAGE) {
            stage();
            da = true;
            drivesystem.deploy_assist();
        }
        frontRight.setPosition(LandingGear.LEGS_STRAIGHT);
        frontLeft.setPosition(LandingGear.LEGS_STRAIGHT);
        backLeft.setPosition (LandingGear.LEGS_STRAIGHT);
        backRight.setPosition(LandingGear.LEGS_STRAIGHT);
        if(da){
            sleep(2000);
            drivesystem.stop();
        }
    }

    public void retract(){
        backRight.setPosition(LandingGear.LEGS_RETRACT);
        backLeft.setPosition(LandingGear.LEGS_RETRACT);
        if(frontLeft.getPosition() < LandingGear.LEGS_RETRACT) {
            sleep(2000);
        }
        frontLeft.setPosition (LandingGear.LEGS_RETRACT);
        frontRight.setPosition(LandingGear.LEGS_RETRACT);

    }

    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs ){
            //twiddle thumbs
        }
    }


    public void deploy(){
//        frontRight.setPosition(LandingGear.LEGS_OUT);
//        frontLeft.setPosition(LandingGear.LEGS_OUT);
//        backLeft.setPosition (LandingGear.LEGS_OUT);
//        backRight.setPosition(LandingGear.LEGS_OUT);

        boolean da = false;
        if(frontLeft.getPosition()>LandingGear.LEGS_STAGE) {
            stage();
            da = true;
            drivesystem.deploy_assist();
        }
        frontRight.setPosition(LEGS_FR_DEPLOY);
        frontLeft.setPosition(LEGS_FL_DEPLOY);
        backLeft.setPosition (LEGS_BL_DEPLOY);
        backRight.setPosition(LEGS_BR_DEPLOY);
        if(da){
            sleep(2000);
            drivesystem.stop();
        }

    }

    public double getState(){
        return backLeft.getPosition();
    }

    //debug functions
    public void setPosition(double position){
        frontRight.setPosition(position);
        frontLeft.setPosition(position);
        backLeft.setPosition (position);
        backRight.setPosition(position);
        log.addData("FrontLeft",frontLeft.getPosition());
        log.addData("FrontRight",frontRight.getPosition());
        log.addData("BackLeft",backLeft.getPosition());
        log.addData("BackRight",backRight.getPosition());
    }
    public void setTelemetry(Telemetry telem){
        log = telem;
    }

}
