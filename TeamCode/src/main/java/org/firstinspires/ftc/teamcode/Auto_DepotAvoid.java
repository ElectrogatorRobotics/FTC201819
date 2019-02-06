
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Depot - Avoid")
public class Auto_DepotAvoid extends LinearOpMode {
    private static final boolean live = false;

	Drive drive = new DriveImpl();
	LandingGear lg = new LandingGearImpl();
    Marker mark = new Marker();

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl();
        drive.setTelemetry(telemetry);
        drive.initMotors(hardwareMap);
        drive.initialiseIMU(hardwareMap);

        mark.init(hardwareMap,telemetry);

        lg.init(hardwareMap,drive);

        telemetry.addLine("Retracting!!!");
        telemetry.update();
        if(live) lg.retract();///!!!Illegal?

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();
        if(live) {
            lg.stand_up();
            drive.slide(4);
            lg.deploy();
        }
        drive.forward(64);
        drive.turn(135);
        mark.KickOutTheMrker();
        drive.forward(55);
        drive.turn(90);
        drive.forward(20);
        drive.turn(45);
        drive.forward(56);
        drive.turn(-90);
        drive.forward(56);
        drive.turn(45);
        drive.forward(20);
        drive.turn(90);
        drive.forward(43);
    }
}
