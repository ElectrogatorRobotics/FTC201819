
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.library.AutoMode;
import org.firstinspires.ftc.teamcode.library.GoldPosition;

/**
 * Created by mira on 11/26/2018.
 */

@Autonomous(name = "Depot marker",group="BackUp")
//@Disabled
public class Auto_DepotMarkerOnly extends AutoMode {


    public void run(GoldPosition gp) {
        return;
    }
    @Override
    public void head_to_depot() {
        telemetry.addLine("Handling Marker");
        mark.KickOutTheMrker();
    }

    @Override
    public void head_to_crater() {
        telemetry.addLine("Heading to Crater");
        drive.forward(-34);
    }

    public void hit_block(GoldPosition gp) {

        drive.forward(34);
    }
}
