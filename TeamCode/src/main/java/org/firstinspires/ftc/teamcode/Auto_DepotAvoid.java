
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.GoldPosition;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;
import org.firstinspires.ftc.teamcode.library.Scoops;
import org.firstinspires.ftc.teamcode.library.ScoopsImpl;
import org.firstinspires.ftc.teamcode.library.TensorID;
import org.firstinspires.ftc.teamcode.library.TensorIDImpl;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Depot - InOut")
public class Auto_DepotAvoid extends LinearOpMode {
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

        tensor = new TensorIDImpl(telemetry, scoop, this);

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        kick_block();
        drive.forward(40);
        return;
        /*turn to avoid*
        drive.forward(40);//head to the split
        drive.turn(-90);//turn right toward lander
        drive.forward(20); //approach lander
        drive.turn(-45);   //turn parallel to the lander
        drive.forward(44); //slide along depot side
        drive.turn(90); //turn to go along the side of the crater
        drive.forward(44);  //drive past the other lander side
        drive.turn(-45);    //turn right to face wall
        drive.forward(20);  //drive to the wall
        drive.turn(-90);    //turn right
        drive.forward(12);  //head to crater
        //*/
    }


    public void kick_block(){
        GoldPosition gp = GoldPosition.UNKNOWN;
        if(live) {
             if(stand)lg.stand_up();
             else{
                 lg.deploy();
             }
             drive.turn(10);
             drive.forward(4);
             if(stand)
                 lg.deploy();
             drive.turn(-11);
        }
        if(scan){
            gp = tensor.getGoldPosition();
        }
        if(gp != GoldPosition.UNKNOWN && scan) {
            switch (gp) {
                case LEFT:
                    drive.turn(-35);
                    break;
                case RIGHT:
                    drive.turn(35);
                    break;
            }
            scoop.moveFrontScoop(250);
            scoop.runRubberBandWheel(1);
            drive.forward(30);
            scoop.moveFrontScoop(-1000);
            scoop.runRubberBandWheel(0);

            switch (gp) {
                case LEFT:
                    drive.turn(100);
                    break;
                case CENTER:
                    drive.turn(180);
                    break;
                case RIGHT:
                    drive.turn(-100);
                    break;
            }
            drive.forward(-40);
            mark.KickOutTheMrker();

            switch (gp) {
                case CENTER:
                    drive.turn(46);
                    break;
                case RIGHT:
                    drive.turn(91);
                    break;
            }
        }
        else{
            drive.turn(-179);
            drive.forward(-50);
            mark.KickOutTheMrker();
        }
    }
}
