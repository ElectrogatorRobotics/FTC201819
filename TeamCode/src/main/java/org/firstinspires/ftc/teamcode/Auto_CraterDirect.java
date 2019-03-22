
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;
import org.firstinspires.ftc.teamcode.library.Scoops;
import org.firstinspires.ftc.teamcode.library.ScoopsImpl;
import org.firstinspires.ftc.teamcode.library.TensorID;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Crater")
public class Auto_CraterDirect extends LinearOpMode {
    private static final boolean live = true;
    private static final boolean stand = false;
    private static final boolean scan = false;

    TensorID tensor;

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
        drive.forward(84);
        //drive.turn(10);
        //drive.forward(30);

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
            drive.turn(-45);
        }
        drive.forward(40);
        drive.turn(90);
        drive.forward(-50);
        //angle at block
        //drive to kill
        //turn to degrees
        //drive to depot
        //line up again
        //drive some more
        //turn to position
        mark.KickOutTheMrker();
    }
}
