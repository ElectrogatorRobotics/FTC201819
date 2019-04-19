
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.library.AutoMode;
import org.firstinspires.ftc.teamcode.library.GoldPosition;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Depot - Direct")
//@Disabled
public class Auto_DepotDirect extends AutoMode {


    public void run(GoldPosition gp) {
        hit_block(gp);
        head_to_depot();
        head_to_crater();
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

        scoop.setFrontTargetPosition(150);
        drive.forward(24);

        switch (gp) {
            case LEFT:
                drive.turn(15);
                drive.forward(3);
                break;
            case RIGHT:
                drive.turn(-15);
                drive.forward(3);
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

        scoop.setFrontTargetPosition(130);
    }
}