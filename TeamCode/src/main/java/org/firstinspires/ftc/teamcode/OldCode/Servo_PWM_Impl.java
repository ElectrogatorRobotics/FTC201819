package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by luke on 11/9/2018.
 */

public class Servo_PWM_Impl implements Servo_PWM {
    private static final int CONVERSION_FACTOR = 3000;

    HardwareMap hardware = null;
//    Servo servo;

    /**
     * max servo PWM values
      */
    public static final int MAX_LEFT  = 200;
    public static final int CENTER    = 1500;
    public static final int MAX_RIGHT = 2800;

    public double PWM_ToRange(int pulseWidth) {
        return ((double)(pulseWidth - 500) / 2000);
    }

    public void InitServo(HardwareMap hardwareMap, String name) {
        hardware = hardwareMap;
        Servo servo = hardware.get(Servo.class, "servo");
    }

    public void setPulseWidth (int pulseWidth, Servo servo) {
        servo.setPosition(PWM_ToRange(pulseWidth));
    }


}
