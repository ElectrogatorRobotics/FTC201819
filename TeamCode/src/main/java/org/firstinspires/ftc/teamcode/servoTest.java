package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by luke on 11/9/2018.
 */

//@TeleOp(name = "servo test")
public class servoTest extends LinearOpMode {
    Servo_PWM servoConverter;
    Servo servo;
    Servo_PWM servoPwm;

    int PWM;
    private static final int LEFT   = 900;
    private static final int CENTER = 1500;
    private static final int RIGHT  = 2100;

    @Override
    public void runOpMode() throws InterruptedException {
        servoConverter = new Servo_PWM_Impl();
        servo = hardwareMap.servo.get("front left servo");
        servo.setPosition(servoConverter.PWM_ToRange(CENTER));

//            servoPwm.InitServo(hardwareMap, "servo");
//            servoPwm = hardwareMap.get(Servo_PWM.class, "servo");
//            servoPwm.setPulseWidth(CENTER, servo);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_right)
                PWM = RIGHT;
            if (gamepad1.dpad_left)
                PWM = LEFT;
            if (gamepad1.dpad_down)
                PWM = CENTER;

            telemetry.addData("PWM",PWM);
            telemetry.addData("Servo value", servo.getPosition());
            telemetry.addData("Servo position", servoConverter.PWM_ToRange(PWM));
            telemetry.update();

            servo.setPosition(servoConverter.PWM_ToRange(PWM));
//            servoPwm.setPulseWidth(PWM, servo);
        }
    }
}
