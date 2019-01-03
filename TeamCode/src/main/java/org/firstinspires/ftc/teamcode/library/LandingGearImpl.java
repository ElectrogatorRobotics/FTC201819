package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LandingGearImpl implements LandingGear {

    public Servo frontRight;
    public Servo frontLeft;
    public Servo backRight;
    public Servo backLeft;
    private Drive drivesystem;

    public void init(HardwareMap hm, Drive drivetrain){
        frontLeft = hm.servo.get("front left servo");
        frontRight = hm.servo.get("front right servo");
        backLeft = hm.servo.get("back left servo");
        backRight = hm.servo.get("back right servo");
        drivesystem = drivetrain;
    }

    public void stand_up(){
        if(frontLeft.getPosition()>LandingGear.LEGS_STAGE) {
            frontRight.setPosition(LandingGear.LEGS_STAGE);
            frontLeft.setPosition(LandingGear.LEGS_STAGE);
            int breakout = 0;
            while (frontLeft.getPosition() > LandingGear.LEGS_STAGE && breakout < 200) {
                sleep(10);
                breakout++;
            }
            backLeft.setPosition(LandingGear.LEGS_STAGE);
            backRight.setPosition(LandingGear.LEGS_STAGE);
            drivesystem.deploy_assist();
        }
        frontRight.setPosition(LandingGear.LEGS_STRAIGHT);
        frontLeft.setPosition(LandingGear.LEGS_STRAIGHT);
        backLeft.setPosition (LandingGear.LEGS_STRAIGHT);
        backRight.setPosition(LandingGear.LEGS_STRAIGHT);
        sleep(10);
        drivesystem.stop();
    }

    public void retract(){
        backRight.setPosition(LandingGear.LEGS_RETRACT);
        backLeft.setPosition(LandingGear.LEGS_RETRACT);
        int breakout =0;
        while(frontLeft.getPosition()<LandingGear.LEGS_RETRACT && breakout<200){
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
}
