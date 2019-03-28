
package org.firstinspires.ftc.teamcode.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OldCode.library.Drive;
import org.firstinspires.ftc.teamcode.OldCode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.OldCode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.OldCode.library.LandingGear;
import org.firstinspires.ftc.teamcode.OldCode.library.LandingGearImpl;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Auto: Pick Me Luke")
@Disabled
public class autoBasic extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();
	Drive drive;
	LandingGear lg= new LandingGearImpl();


    double maxDrive = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl();
        hardware.initMotors(hardwareMap);

        drive.setTelemetry(telemetry);
        drive.initMotors(hardwareMap);
        lg.init(hardwareMap,drive,this);

        telemetry.addLine("Retracting!!!");
        telemetry.update();
        lg.retract();///!!!Illegal?

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();
        lg.stand_up();
        //drive.slide_time(-250);
        drive.slide(3);
        lg.deploy();
        drive.forward(6);
        drive.turn(90);
        drive.forward(4);
    }

    void setMaxDrive(double motor) {
        if (Math.abs(motor) > maxDrive) {
            maxDrive = Math.abs(motor);
        }
    }
}
