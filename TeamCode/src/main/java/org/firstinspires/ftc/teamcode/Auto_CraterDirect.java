
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.AutoMode;
import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.GoldPosition;
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
@Disabled
public class Auto_CraterDirect extends AutoMode {


    public void run(GoldPosition gp) {
        head_to_depot();
        return_to_start();
        hit_block(gp);
        head_to_crater();
    }

    @Override
    public void head_to_depot() {
        telemetry.addLine("Handling Marker");
        drive.forward(12);
        drive.turn(-90);
        drive.forward(40);
        drive.turn(-45);
        drive.forward(40);
        mark.KickOutTheMrker();
    }

    public void return_to_start(){
        drive.forward(-40);
        drive.turn(45);
        drive.forward(-40);
        drive.turn(90);
    }

    @Override
    public void head_to_crater() {
        telemetry.addLine("Heading to Crater");
        drive.turn(135);
        drive.forward(85);
    }

    public void hit_block(GoldPosition gp) {
        telemetry.addData("Target block: ", gp.name());
        telemetry.update();
        switch (gp) {
            case LEFT:
                drive.turn(-45);
                break;
            case RIGHT:
                drive.turn(45);
                break;
        }
        scoop.moveFrontScoop(250);
        drive.forward(12);
        scoop.moveFrontScoop(-1000);
        switch (gp) {
            case LEFT:
                drive.forward(-12);
                drive.turn(45);
                drive.forward(20);
                break;
            case CENTER:
                drive.forward(8);
            case RIGHT:
                drive.forward(-12);
                drive.turn(-45);
                drive.forward(20);
                break;
        }
    }
}
