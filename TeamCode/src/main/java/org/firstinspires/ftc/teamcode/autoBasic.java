
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Auto: Pick Me Luke")
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

        double adjFactor;
        double throtle;

        drive = new DriveImpl();

        hardware.initMotors(hardwareMap);
       lg.init(hardwareMap);
        drive.initMotors(hardware);
        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

           

            telemetry.addData("Front right drive speed = ", "%1.2f", frontRightDrive);
            telemetry.addData("Front left drive speed  = ", "%1.2f", frontLeftDrive);
            telemetry.addData("Back right drive speed  = ", "%1.2f", backRightDrive);
            telemetry.addData("Back left drive speed   = ", "%1.2f", backLeftDrive);
//            telemetry.addData("Front right drive speed = ", "%1.2f", hardware.frontRightDrive.getPower());
//            telemetry.addData("Front left drive speed  = ", "%1.2f", hardware.frontLeftDrive.getPower());
//            telemetry.addData("Back right drive speed  = ", "%1.2f", hardware.backRightDrive.getPower());
//            telemetry.addData("Back left drive speed   = ", "%1.2f", hardware.backLeftDrive.getPower());
// 	        telemetry.addData("Throttle                = ", "%1.2f", drive.throttleControl(gamepad1.left_trigger, drive.MIN_SPEED));
	        telemetry.addData("Throttle                = ", "%1.2f", throtle);
            telemetry.update();
        }
    }

    void setMaxDrive(double motor) {
        if (Math.abs(motor) > maxDrive) {
            maxDrive = Math.abs(motor);
        }
    }
}
