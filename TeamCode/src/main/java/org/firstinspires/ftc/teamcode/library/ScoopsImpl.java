package org.firstinspires.ftc.teamcode.library;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ScoopsImpl implements Scoops {

    public Servo frontScoop;
    public Servo backScoop;
    private Telemetry log;
    private ElapsedTime runtime = new ElapsedTime();

    public void init(HardwareMap hm, Telemetry telm){
        frontScoop = hm.servo.get("front scoop");
        backScoop = hm.servo.get("back scoop");
        log = telm;
    }

    public void setFrontScoopPos(double pos){
        frontScoop.setPosition(pos);
    }

    public void frontScoopDown(){
        setFrontScoopPos(FRONT_DOWN);
    }

    public void frontScoopTransfer(){
        if(backScoop.getPosition() > BACK_DOWN){
            setBackScoopPos(BACK_DOWN);
            sleep(500);
        }
        setFrontScoopPos(FRONT_TRANSFER);
    }

    public void frontScoopCycle(){
        frontScoopTransfer();
        sleep(1100);
        frontScoopDown();
    }


    //Back Scoops
    public void setBackScoopPos(double pos){
        backScoop.setPosition(pos);
    }

    public void backScoopDown(){
        if(frontScoop.getPosition() > FRONT_AVOID){
            setFrontScoopPos(FRONT_AVOID);
            sleep(500);
        }
        setBackScoopPos(BACK_DOWN);
    }

    public void backScoopDump(){
        if(frontScoop.getPosition() > FRONT_AVOID){
            setFrontScoopPos(FRONT_AVOID);
            sleep(500);
        }
        setBackScoopPos(BACK_DUMP);
    }

    public void backScoopCycle(){
        backScoopDump();
        sleep(1100);
        backScoopDown();
    }

    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs ){
            Thread.yield();
        }
    }
}
