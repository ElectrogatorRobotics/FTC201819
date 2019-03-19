
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "Servo test")
public class TeleOp_ServoTest extends LinearOpMode {
    public Servo s1 = null;
    public Servo s2 = null;
    public static final double WHEELS_UP = 0.8;
    public static final double WHEELS_DOWN = 0.3;
    public static final double WHEELS_OUT = 0.2;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();


        s1 = hardwareMap.servo.get("servo 1");
        s2 = hardwareMap.servo.get("servo 2");
        s1.setDirection(Servo.Direction.FORWARD);
        s2.setDirection(Servo.Direction.FORWARD);
        s1.getController().pwmEnable();
        s2.getController().pwmEnable();

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();
            double a=.5;

        while (opModeIsActive()) {

            if (gamepad1.left_bumper) {
                if (a<.8)
                         a += .1;
            }
            if (gamepad1.right_bumper){
                if (a > .2) a-= .1;
            }

            s1.setPosition(a);
            // display the motor speeds

            telemetry.addData("servo 1 position = ", "%1.2f",a);
            telemetry.update();
        }
    }

}
