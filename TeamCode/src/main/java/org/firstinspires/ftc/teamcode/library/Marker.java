package org.firstinspires.ftc.teamcode.library;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Marker {

    public static final long CYCLE_TIME = 200;

    public Servo mServo;

    private Telemetry log;
    private ElapsedTime runtime = new ElapsedTime();
    private ScoringArms scoops;

    public Marker(ScoringArms sa, Telemetry telm) {
        init(sa, telm);
    }
    public void init(ScoringArms sa, Telemetry telm){
        scoops = sa;
        log = telm;
    }

    public void setMarkerPos(double pos){
        mServo.setPosition(pos);
    }

    public void KickOutTheMrker(){
        log.addLine("MARKER = EJECTED!");
        scoops.setScoringArmServoPosition(true);
        sleep(CYCLE_TIME);
        scoops.setScoringArmServoPosition(false);
        sleep(CYCLE_TIME);
        scoops.setScoringArmServoPosition(true);
        sleep(CYCLE_TIME);
        scoops.setScoringArmServoPosition(false);
    }


    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs ){
            //twiddle thumbs
            Thread.yield();
        }
    }
}
