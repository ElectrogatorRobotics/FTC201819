
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@Autonomous(name = "Crater - Avoid")
@Disabled
public class Auto_CraterAvoid extends LinearOpMode {
    private static final boolean live = false;
    private static final boolean stand = false;
    private static final boolean scan = true;

    TensorID tensor = new TensorIDImpl();

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

        tensor.initTensorID( telemetry, scoop, this );

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        //kick_block
        //turn to avoid
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
    }


    public void kick_block(){
        if(live) {
            if(stand)lg.stand_up();
            else{
                lg.deploy();
            }
            drive.turn(-45);
            //drive.forward(2);
            if(stand)
                lg.deploy();
            //drive.turn(-45);
        }
        GoldPosition gp = tensor.getGoldPosition();
        if(gp != GoldPosition.UNKNOWN && scan) {
            switch (gp) {
                case LEFT:
                    drive.turn(-23);
                    break;
                case CENTER:
                    drive.turn(5);
                case RIGHT:
                    drive.turn(45);
                    break;
            }
            scoop.moveFrontScoop(250);
            scoop.runRubberBandWheel(1);
            drive.forward(24);
            scoop.moveFrontScoop(-1000);
            scoop.runRubberBandWheel(0);

            drive.forward(-24);

            switch (gp) {
                case LEFT:
                    drive.turn(-67);
                case CENTER:
                    drive.turn(-95);
                case RIGHT:
                    drive.turn(-135);
            }
            drive.forward(28);
            drive.turn(-90);
            drive.forward(44);
            drive.turn(45);
            drive.forward(20);
            drive.turn(-90);
            drive.forward(-40);
            mark.KickOutTheMrker();
        }
        else{
            drive.turn(-45);
            drive.forward(40);
            drive.turn(-135);
            mark.KickOutTheMrker();
        }
    }
}
