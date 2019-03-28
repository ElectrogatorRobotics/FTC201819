package org.firstinspires.ftc.teamcode.OldCode.library;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Marker {

    public Servo mServo;

    public static final double DUMP = 0.75;
    public static final double PARK = 0.3;

    private Telemetry log;
    private ElapsedTime runtime = new ElapsedTime();

    public void init(HardwareMap hm, Telemetry telm){
        mServo = hm.servo.get("Marker");
        log = telm;
    }

    public void setMarkerPos(double pos){
        mServo.setPosition(pos);
    }

    public void KickOutTheMrker(){
        log.addLine("MARKER = EJECTED!");
        setMarkerPos(DUMP);
        sleep(1000);
        setMarkerPos(PARK);
    }


    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs ){
            //twiddle thumbs
            Thread.yield();
        }
    }
}
