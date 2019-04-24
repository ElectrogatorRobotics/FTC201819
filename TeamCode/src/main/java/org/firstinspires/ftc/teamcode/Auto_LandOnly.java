
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.library.AutoMode;
import org.firstinspires.ftc.teamcode.library.GoldPosition;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "land only",group="BackUp")
//@Disabled
public class Auto_LandOnly extends AutoMode {


    public void run(GoldPosition gp) {
        return;
    }

    @Override
    public void head_to_depot() {
        telemetry.addLine("Handling Marker");
//        mark.KickOutTheMrker();
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
                drive.turn(-10);
                break;
            case RIGHT:
                drive.turn(10);
                break;
        }
//        scoop.moveFrontScoop(250);
        drive.forward(24);
//        scoop.moveFrontScoop(-1000);
        switch (gp) {
            case LEFT:
                drive.turn(15);
                drive.forward(10);
                break;
            case RIGHT:
                drive.turn(-15);
                drive.forward(10);
                break;
        }
        drive.forward(10);
        switch (gp) {
            case LEFT:
                drive.turn(-5);
                break;
            case RIGHT:
                drive.turn(5);
                break;
        }
    }
}
