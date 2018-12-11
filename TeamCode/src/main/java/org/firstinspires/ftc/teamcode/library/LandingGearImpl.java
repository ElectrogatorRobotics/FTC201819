package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LandingGearImpl implements LandingGear {

    public Servo frontRight;
    public Servo frontLeft;
    public Servo backRight;
    public Servo backLeft;
    private Telemetry log;

    public void init(HardwareMap hm){
        frontLeft = hm.servo.get("front left servo");
        frontRight = hm.servo.get("front right servo");
        backLeft = hm.servo.get("back left servo");
        backRight = hm.servo.get("back right servo");
    }

    public void stand_up(){
        frontRight.setPosition(LandingGear.LEGS_STRAIGHT);
        frontLeft.setPosition(LandingGear.LEGS_STRAIGHT);
        int breakout =0;
        while(backRight.getPosition()>LandingGear.LEGS_STRAIGHT && breakout<200){
            sleep(10);
            breakout ++;
        }
        backLeft.setPosition (LandingGear.LEGS_STRAIGHT);
        backRight.setPosition(LandingGear.LEGS_STRAIGHT);

    }

    public void retract(){
        backRight.setPosition(LandingGear.LEGS_RETRACT);
        backLeft.setPosition(LandingGear.LEGS_RETRACT);
        int breakout =0;
        while(frontRight.getPosition()<LandingGear.LEGS_RETRACT && breakout<200){
            sleep(10);
            breakout ++;
        }
        frontLeft.setPosition (LandingGear.LEGS_RETRACT);
        frontRight.setPosition(LandingGear.LEGS_RETRACT);

    }

    private void sleep(long milisecs) {
       try {
           Thread.sleep(milisecs);
       }
       catch(Exception e){}
    }


    public void deploy(){
        frontRight.setPosition(LandingGear.LEGS_OUT);
        frontLeft.setPosition(LandingGear.LEGS_OUT);
        backLeft.setPosition (LandingGear.LEGS_OUT);
        backRight.setPosition(LandingGear.LEGS_OUT);

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
