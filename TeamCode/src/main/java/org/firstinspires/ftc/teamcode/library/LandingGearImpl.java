package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class LandingGearImpl implements LandingGear {

    public Servo frontRight;
    public Servo frontLeft;
    public Servo backRight;
    public Servo backLeft;
    private Drive drivesystem;
    private ElapsedTime runtime = new ElapsedTime();


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
            sleep(2000);
            backLeft.setPosition(LandingGear.LEGS_STAGE);
            backRight.setPosition(LandingGear.LEGS_STAGE);
            sleep(1000);
            drivesystem.deploy_assist();
        }
        frontRight.setPosition(LandingGear.LEGS_STRAIGHT);
        frontLeft.setPosition(LandingGear.LEGS_STRAIGHT);
        backLeft.setPosition (LandingGear.LEGS_STRAIGHT);
        backRight.setPosition(LandingGear.LEGS_STRAIGHT);
        sleep(500);
        drivesystem.stop();
    }

    public void retract(){
        backRight.setPosition(LandingGear.LEGS_RETRACT);
        backLeft.setPosition(LandingGear.LEGS_RETRACT);
        int breakout =0;
        sleep(2000);
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

        frontRight.setPosition(0.2);
        frontLeft.setPosition(0.18);
        backLeft.setPosition (0.17);
        backRight.setPosition(0.2);

    }


    public double getState(){
        return backLeft.getPosition();
    }
}
