package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LandingGearImpl implements LandingGear {

    public Servo front;
    public Servo back;
    private Telemetry log;
    private Drive drivesystem;
    private LinearOpMode lom;
    private ElapsedTime runtime = new ElapsedTime();


    public void init(HardwareMap hm, Drive drivetrain, LinearOpMode lop){
        front = hm.servo.get("front servo");
        back = hm.servo.get("back servo");
        // scale the range of the 0-1 signal to match the range of the servos
        back.scaleRange(0.2, 0.82);
        front.scaleRange(0.2, 0.79);
        drivesystem = drivetrain;
        lom = lop;
    }

    private void stage(){
        front.setPosition(LandingGear.LEGS_STAGE);
        sleep(2000);
        back.setPosition(LandingGear.LEGS_STAGE);
        sleep(1000);
    }

    public void stand_up(){
        boolean da = false;
        if(front.getPosition()>LandingGear.LEGS_STAGE) {
            stage();
            da = true;
            drivesystem.deploy_assist();
        }
        front.setPosition(LandingGear.LEGS_STRAIGHT);
        back.setPosition(LandingGear.LEGS_STRAIGHT);
        if(da){
            sleep(500);
            drivesystem.stop();
        }
    }

    public void retract(){
        back.setPosition(LandingGear.LEGS_RETRACT);
        if(back.getPosition() < LandingGear.LEGS_RETRACT) {
            sleep(1000);
        }
        front.setPosition(LandingGear.LEGS_RETRACT);

    }

    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs && lom.opModeIsActive()){
            Thread.yield();
        }
    }


    public void deploy(){
        drivesystem.setBreak(false);
//        frontRight.setPosition(LandingGear.LEGS_OUT);
//        frontLeft.setPosition(LandingGear.LEGS_OUT);
//        backLeft.setPosition (LandingGear.LEGS_OUT);
//        backRight.setPosition(LandingGear.LEGS_OUT);

        boolean da = true;
        if(front.getPosition()>LandingGear.LEGS_STAGE) {
            stage();
            da = true;
            drivesystem.deploy_assist();
        }
        drivesystem.deploy_assist();
        front.setPosition(LEGS_FR_DEPLOY);
        back.setPosition(LEGS_BR_DEPLOY);
        if(da){
            sleep(500);
            drivesystem.stop();
        }

    }

    public double getState(){
        return back.getPosition();
    }

    //debug functions
    public void setPosition(double position){
        front.setPosition(position);
        back.setPosition(position);
        log.addData("Front",front.getPosition());
        log.addData("Back",back.getPosition());
    }
    public void initTelemetry(Telemetry telem){
        log = telem;
    }

}
