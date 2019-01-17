package org.firstinspires.ftc.teamcode.library;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class marker {

    public Servo mServo;

    private Telemetry log;
    private ElapsedTime runtime = new ElapsedTime();

    public void init(HardwareMap hm, Telemetry telm){
        mServo = hm.servo.get("marker");
        log = telm;
    }

    public void setMarkerPos(double pos){
        mServo.setPosition(pos);
    }

    public void KickOutTheMrker(){
        setMarkerPos(1);
        sleep(1000);
        setMarkerPos(0);
    }


    private void sleep(long milisecs) {
        runtime.reset();
        while(runtime.milliseconds() < milisecs ){
            //twiddle thumbs
            Thread.yield();
        }
    }
}
