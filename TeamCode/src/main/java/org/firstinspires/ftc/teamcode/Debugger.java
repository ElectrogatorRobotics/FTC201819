
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;
import org.firstinspires.ftc.teamcode.library.Scoops;
import org.firstinspires.ftc.teamcode.library.ScoopsImpl;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "Debugger")
@Disabled
public class Debugger extends LinearOpMode {

	double position = 0.3;

    Drive drive;
    LandingGear lg;
    Marker mark;
    Scoops scoop;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl(hardwareMap,telemetry,this);

        mark = new Marker(hardwareMap,telemetry);

        lg = new LandingGearImpl(hardwareMap,drive,this);

        scoop = new ScoopsImpl(hardwareMap, telemetry);
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
                ((LandingGearImpl)lg).setPosition(position);
            }

            if(gamepad1.dpad_up) drive.forward(5);
            else if(gamepad1.dpad_left) drive.turn(30);
            else if(gamepad1.dpad_right) drive.slide(5);

            telemetry.update();
        }
    }
}
