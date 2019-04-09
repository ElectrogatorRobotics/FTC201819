package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by luke on 11/9/2018.
 */

public interface Servo_PWM {
    double PWM_ToRange(int pulseWidth);
    void InitServo (HardwareMap hardwareMap, String name);
    void setPulseWidth (int pulseWidth, Servo servo);
}
