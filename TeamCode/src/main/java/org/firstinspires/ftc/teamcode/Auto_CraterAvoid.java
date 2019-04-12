
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.AutoMode;
import org.firstinspires.ftc.teamcode.library.GoldPosition;
import org.firstinspires.ftc.teamcode.library.LandingGear;
import org.firstinspires.ftc.teamcode.library.Scoops;
import org.firstinspires.ftc.teamcode.library.ScoopsImpl;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Crater - Avoid")
@Disabled
public class Auto_CraterAvoid extends AutoMode {


    public void run(GoldPosition gp){
        head_to_depot();
        head_to_crater();
        hit_block(gp);
        finish_head_to_crater(gp);
    }

    @Override
    public void head_to_depot(){
        telemetry.addLine("Heading to Depot");
        drive.forward(28);
        drive.turn(-90);
        drive.forward(44);
        drive.turn(45);
        drive.forward(20);
        drive.turn(-90);
        drive.forward(-40);
        mark.KickOutTheMrker();
    }

    @Override
    public void head_to_crater() {
        telemetry.addLine("Heading to Crater");
        drive.turn(-45);
        drive.forward(40);
        drive.turn(-135);
    }

    public void hit_block(GoldPosition gp) {
        telemetry.addData("Target block: ",gp.name());
        telemetry.update();
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
    }


    public void finish_head_to_crater(GoldPosition gp) {
        telemetry.addData("Getting in crater: ",gp.name());
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
    }
}
