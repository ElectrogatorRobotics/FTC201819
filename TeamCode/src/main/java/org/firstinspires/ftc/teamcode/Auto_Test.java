package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.DriveV2;
import org.firstinspires.ftc.teamcode.library.DriveV2_Impl;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;
import org.firstinspires.ftc.teamcode.library.ScoringArms;
import org.firstinspires.ftc.teamcode.library.ScoringArmsImpl;
import org.firstinspires.ftc.teamcode.library.TensorID;
import org.firstinspires.ftc.teamcode.library.TensorIDImpl;

@TeleOp
@Disabled
public class Auto_Test extends LinearOpMode {
    protected Drive drive;
    protected DriveV2 drive2;
    protected LandingGear lg;
    protected ScoringArms scoop;
    protected TensorID tensor;
    ElapsedTime runtime;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        runtime = new ElapsedTime();
        drive2 = new DriveV2_Impl();
        drive2.initDrive(hardwareMap);
        drive2.init_bno055IMU(hardwareMap);
        drive = new DriveImpl(drive2, telemetry,this);

        lg = new LandingGearImpl(drive2, drive,this);
        scoop = new ScoringArmsImpl();
        scoop.initScoringSystems(hardwareMap);

        Marker marker = new Marker(scoop,telemetry);

//        tensor = new TensorIDImpl(telemetry, this);

//        telemetry.addLine("Retracting!!!");
//        drive2.driveServoState(DriveV2.driveServoState.RETRACT);

        telemetry.addLine("Ready to run.....");
        telemetry.update();

        //Wait for the system to start auto
        waitForStart();
        runtime.reset();

//        lg.deploy();
//        while (runtime.milliseconds() < 3000 && opModeIsActive()) {
//            idle();
//        }
//        telemetry.addLine("sliding");
//        telemetry.update();
//        drive.slide(3);
//        drive.forward(12);

        drive.turn(90);

//        drive.turn(100);


//        scoop.setFrontTargetPosition(150);
//        scoop.setIntakeMotorPower(1);
//        scoop.waitForMoveEnd();
//        sleep(5000);
//        scoop.setIntakeMotorPower(0.0);
//        scoop.setFrontTargetPosition(-45);
//        scoop.setIntakeArmMotorPower(0.5);
//        Thread.sleep(1500);
//        scoop.setIntakeArmMotorPower(0);
//     drive.slide(12);
//        marker.KickOutTheMrker();
        Thread.sleep(30000);
    }
}
