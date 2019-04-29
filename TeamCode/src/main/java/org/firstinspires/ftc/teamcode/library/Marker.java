package org.firstinspires.ftc.teamcode.library;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
    private LinearOpMode lom;

    public Marker(ScoringArms sa, Telemetry telm, LinearOpMode op) {
        init(sa, telm, op);
    }
    public void init(ScoringArms sa, Telemetry telm, LinearOpMode op){
        scoops = sa;
        log = telm;
        lom = op;
    }

    public void setMarkerPos(double pos){
        mServo.setPosition(pos);
    }

    public void KickOutTheMrker() {
        log.addLine("MARKER = EJECTED!");
//        scoops.setScoringArmServoPosition(true);
//        sleep(CYCLE_TIME);
//        scoops.setScoringArmServoPosition(false);
//        sleep(CYCLE_TIME);
//        scoops.setScoringArmServoPosition(true);
//        sleep(CYCLE_TIME);
//        scoops.setScoringArmServoPosition(false);

        runtime.reset();
        scoops.setIntakeMotorPower(1);
//        do {
//            Thread.yield();
//        } while (lom.opModeIsActive() && runtime.milliseconds()<1000);
        sleep(1500);
        scoops.setIntakeMotorPower(0.0);
    }


    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs ){
            //twiddle thumbs
            Thread.yield();
        }
    }
}
