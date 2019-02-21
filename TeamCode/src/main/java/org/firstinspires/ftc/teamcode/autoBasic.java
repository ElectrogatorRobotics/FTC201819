
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Auto: Just Deploy")
public class autoBasic extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();
	Drive drive;
	LandingGear lg= new LandingGearImpl();
    Marker mark = new Marker();


    double maxDrive = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl();
        hardware.initMotors(hardwareMap);

        mark.init(hardwareMap,telemetry);

        drive.setTelemetry(telemetry);
        drive.initMotors(hardwareMap);
        drive.initialiseIMU(hardwareMap);
        drive.passLinearOp(this);

        lg.init(hardwareMap,drive,this);

        telemetry.addLine("Retracting!!!");
        telemetry.update();
        lg.retract();///!!!Illegal?

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();
        lg.stand_up();
//        drive.turn(-20);
        drive.turn(-179);
        lg.deploy();
        //drive.forward(6);
        //drive.turn(-25);
        drive.forward(-60);
        mark.KickOutTheMrker();
        drive.forward(50);
    }

    void setMaxDrive(double motor) {
        if (Math.abs(motor) > maxDrive) {
            maxDrive = Math.abs(motor);
        }
    }
}
