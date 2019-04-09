package org.firstinspires.ftc.teamcode.OldCode.library;


import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ScoopsImpl implements Scoops {

    public DcMotorSimple frontScoop;
    public Servo backScoop;
    public Servo frontScoopWheel;
    public DistanceSensor frontScoopDistance;

    private Telemetry log;
    private ElapsedTime runtime = new ElapsedTime();
    private Boolean cycling;

    public ScoopsImpl(HardwareMap hm, Telemetry telm) {
        init(hm,telm);
    }
    public void init(HardwareMap hm, Telemetry telm){
        frontScoop = hm.get(DcMotorSimple.class,"front scoop");
        backScoop = hm.servo.get("back scoop");
        frontScoopWheel = hm.servo.get("wheel servo");
        //frontScoopDistance = hm.get(DistanceSensor.class, "front distance");
        log = telm;
        cycling = false;
    }

    public double frontScoopDownWithSensor () {
        double distance;
        if (frontScoopDistance.getDistance(DistanceUnit.INCH)>0)
            distance = frontScoopDistance.getDistance(DistanceUnit.INCH);
        else distance = 0;

        while (distance > 2.5) {
            setFrontScoopPos(0.5);
        }
        return frontScoopDistance.getDistance(DistanceUnit.INCH);
    }
    public void setFrontScoopPos(double pos){
        frontScoop.setPower(pos);
    }

    public void moveFrontScoop(long time){
        double power = 0.5;
        if(time < 0){
            power = -0.5;
        }
        time = Math.abs(time);
        setFrontScoopPos(power);
        sleep(time);
        setFrontScoopPos(0.0);
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

    private void sleep(long milliseconds){
        ElapsedTime runtime = new ElapsedTime();
        double time;
        do {
            time = runtime.milliseconds();
            Thread.yield(); //effectively what the LinearOpMode idle call does
        } while (time < milliseconds);
    }

    public void runRubberBandWheel (double speed) {
        frontScoopWheel.setPosition(speed);
    }

}
