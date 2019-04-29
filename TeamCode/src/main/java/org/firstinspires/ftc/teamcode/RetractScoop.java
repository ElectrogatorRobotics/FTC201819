package org.firstinspires.ftc.teamcode;

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

@TeleOp
//@Disabled
public class RetractScoop extends LinearOpMode {
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

        Marker marker = new Marker(scoop,telemetry, this);

        waitForStart();

        while(opModeIsActive())

            scoop.setIntakeArmMotorPower(.5);

    }
}
