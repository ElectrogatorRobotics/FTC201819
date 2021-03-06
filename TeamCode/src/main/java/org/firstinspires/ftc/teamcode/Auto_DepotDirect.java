
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
//        scoop.setIntakeMotorPower(.5);
//        head_to_depot();
        scoop.setIntakeMotorPower(0);
        drive.forward(-18);
//        head_to_crater();
    }

    @Override
    public void head_to_depot() {
        telemetry.addLine("Handling Marker");
        mark.KickOutTheMrker();
    }

    @Override
    public void head_to_crater() {
        telemetry.addLine("Heading to Crater");
        drive.turn(-135);
        scoop.moveFrontArmTime(1000);

        drive.forward(85);
    }

    public void hit_block(GoldPosition gp) {
        telemetry.addData("Target block: ", gp.name());
        telemetry.update();
        switch (gp) {
            case LEFT:
                drive.turn(14);
                break;
            case RIGHT:
                drive.turn(-14);
                break;
        }

        scoop.setFrontTargetPosition(150);
        drive.forward(24);

        switch (gp) {
            case LEFT:
                drive.turn(-5);
                drive.forward(3);
                break;
            case RIGHT:
                drive.turn(5);
                drive.forward(3);
                break;
        }
        drive.forward(10);
        switch (gp) {
            case LEFT:
            case RIGHT:
                drive.turn(0);
                break;
        }

        scoop.setFrontTargetPosition(130);

        mark.KickOutTheMrker();
    }
}

