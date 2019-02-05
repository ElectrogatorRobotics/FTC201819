
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Depot - Direct")
public class Auto_DepotDirect extends LinearOpMode {
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

        drive.forward(50);
        drive.turn(132);
        mark.KickOutTheMrker();
        drive.forward(83);
    }
}
