package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;

@TeleOp
public class RetractServos extends LinearOpMode {
    Drive drive;
    LandingGear landingGear;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl();
        landingGear = new LandingGearImpl();
        drive.setTelemetry(telemetry);
        drive.initMotors(hardwareMap);
        drive.initialiseIMU(hardwareMap);
        drive.passLinearOp(this);
        landingGear.init(hardwareMap, drive, this);

        waitForStart();

        if (opModeIsActive()) {
            landingGear.stand_up();
            sleep(1000);
            landingGear.retract();
            sleep(1000);
        }
    }
}
