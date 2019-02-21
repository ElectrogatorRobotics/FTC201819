
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;
import org.firstinspires.ftc.teamcode.library.Scoops;
import org.firstinspires.ftc.teamcode.library.ScoopsImpl;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Depot - Direct")
@Disabled
public class Auto_DepotDirect extends LinearOpMode {
    private static final boolean live = false;
    private static final boolean stand = false;


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


        telemetry.addLine("Retracting!!!");
        telemetry.update();
        if(live) lg.retract();///!!!Illegal?

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        kick_block();

        //turn to direct angle
        drive.forward(72);
        //drive.turn(-90);

    }

    public void kick_block(){
        if(live && stand) {
            lg.stand_up();
            drive.turn(10);
        }
        if(live){
            lg.deploy();
        }
        if(live && stand) {
            drive.turn(35);
        }
        else{
            drive.turn(45);
        }
        drive.forward(12);
        //read Vuforia
        // update our position
        drive.forward(-12);
        //angle at block
        //drive to kill
        //turn to degrees
        //drive to depot
        //turn to position
        mark.KickOutTheMrker();
    }
}
