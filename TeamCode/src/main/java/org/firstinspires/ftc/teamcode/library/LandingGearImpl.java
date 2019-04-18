package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LandingGearImpl implements LandingGear {

    private LinearOpMode lom;
    private Drive drivetrain;
    private DriveV2 drive2;


    public LandingGearImpl(DriveV2 d2, Drive drive, LinearOpMode lop) {
        init(d2, drive, lop);
    }
    public void init(DriveV2 v2, Drive drive, LinearOpMode lop){
        drive2 = v2;

        drivetrain = drive;

        lom = lop;
    }

    public void stand_up(){
        drive2.driveServoState(DriveV2.driveServoState.STRAIGHT);
    }

    public void retract(){
        drive2.driveServoState(DriveV2.driveServoState.RETRACT);
    }

    public void deploy(){
        drive2.driveServoState(DriveV2.driveServoState.DRIVE);
    }

    public void unhook(){
        stand_up();
        drivetrain.slide(6);
        deploy();
    }

}
