
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.LandingGearImpl;
import org.firstinspires.ftc.teamcode.library.Marker;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Crater - Avoid")
public class Auto_CraterAvoid extends LinearOpMode {
    private static final boolean live = false;
    private static final boolean stand = false;

	Drive drive = new DriveImpl();
	LandingGear lg = new LandingGearImpl();
    Marker mark = new Marker();

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        drive = new DriveImpl();
        drive.setTelemetry(telemetry);
        drive.initMotors(hardwareMap);
        drive.initialiseIMU(hardwareMap);
        drive.passLinearOp(this);

        mark.init(hardwareMap,telemetry);

        lg.init(hardwareMap,drive,this);

        telemetry.addLine("Retracting!!!");
        telemetry.update();
        if(live) lg.retract();///!!!Illegal?

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        //kick_block
        //turn to avoid
        drive.forward(36);//head to the split
        drive.turn(-90);//turn right toward lander
        drive.forward(20); //approach lander
        drive.turn(-45);   //turn parallel to the lander
        drive.forward(56); //slide along depot side
        drive.turn(90); //turn to go along the side of the crater
        drive.forward(56);  //drive past the other lander side
        drive.turn(-45);    //turn right to face wall
        drive.forward(20);  //drive to the wall
        drive.turn(-90);    //turn right
        drive.forward(43);  //head to crater
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
