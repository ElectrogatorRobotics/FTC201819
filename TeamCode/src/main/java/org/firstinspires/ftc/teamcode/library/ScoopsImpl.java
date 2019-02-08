package org.firstinspires.ftc.teamcode.library;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ScoopsImpl implements Scoops {

    public DcMotorSimple frontScoop;
    public Servo backScoop;
    public Servo frontScoopWheel;

    private Telemetry log;
    private ElapsedTime runtime = new ElapsedTime();
    private Boolean cycling;

    public void init(HardwareMap hm, Telemetry telm){
        frontScoop = hm.get(DcMotorSimple.class,"front scoop");
        backScoop = hm.servo.get("back scoop");
        frontScoopWheel = hm.servo.get("wheel servo");
        log = telm;
        cycling = false;
    }

    public void setFrontScoopPos(double pos){
        frontScoop.setPower(pos);
    }

    //Back Scoops
    public void setBackScoopPos(double pos){
        backScoop.setPosition(pos);
    }

    public void backScoopDown(){
//        if(frontScoop.getPosition() > FRONT_AVOID){
//            setFrontScoopPos(FRONT_AVOID);
//            sleep(500);
//        }
        setBackScoopPos(BACK_DOWN);
    }

    public void backScoopDump(){
//        if(frontScoop.getPosition() > FRONT_AVOID){
//            setFrontScoopPos(FRONT_AVOID);
//            sleep(500);
//        }
        setBackScoopPos(BACK_DUMP);
    }

    public void backScoopCycle(){
        if (! cycling){
            cycling=true;
            runtime.reset();
            backScoopDump();
        }
    }

    public void checkCycling() {
        if (cycling){
            if(runtime.milliseconds() > BACK_CYCLE_TIME ){
                backScoopDown();
                cycling=false;
            }
        }
    }

    public void runRubberBandWheel (double speed) {
        frontScoopWheel.setPosition(speed);
    }

}
