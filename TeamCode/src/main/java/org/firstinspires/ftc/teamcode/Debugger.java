
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "Debugger")
public class Debugger extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();
	LandingGearImpl lg= new LandingGearImpl();

	double position = 0.3;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        hardware.initMotors(hardwareMap);
       lg.init(hardwareMap);
       lg.setTelemetry(telemetry);
       Drive go = new DriveImpl();
        go.setTelemetry(telemetry);
       go.initMotors(hardwareMap);

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.a)lg.stand_up();
            else if (gamepad1.b)lg.deploy();
            else if(gamepad1.x)lg.retract();

            if(gamepad1.left_bumper || gamepad1.right_bumper) {
                if (gamepad1.left_bumper) position -= 0.02;
                else if (gamepad1.right_bumper) position += 0.02;
                while(gamepad1.left_bumper || gamepad1.right_bumper);
                lg.setPosition(position);
            }

            if(gamepad1.dpad_up) go.forward(5);
            else if(gamepad1.dpad_left) go.turn(30);
            else if(gamepad1.dpad_right) go.slide(5);

            telemetry.update();
        }
    }
}
